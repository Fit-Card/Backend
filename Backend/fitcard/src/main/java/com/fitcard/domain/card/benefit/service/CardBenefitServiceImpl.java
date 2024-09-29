package com.fitcard.domain.card.benefit.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.benefit.exception.SaveBenefitsFromFinancialException;
import com.fitcard.domain.card.benefit.model.CardBenefit;
import com.fitcard.domain.card.benefit.model.dto.response.*;
import com.fitcard.domain.card.benefit.repository.CardBenefitRepository;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.performance.exception.SavePerformancesFromFinancialException;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.model.dto.response.FinancialCardPerformanceResponses;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.card.version.repository.CardVersionRepository;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CardBenefitServiceImpl implements CardBenefitService {

    private final CardBenefitRepository cardBenefitRepository;
    private final CardPerformanceRepository cardPerformanceRepository;
    private final String FINANCIAL_GET_BENEFITS;
    private final RestClient restClient;
    private final CardVersionRepository cardVersionRepository;
    private final MerchantInfoRepository merchantInfoRepository;

    public CardBenefitServiceImpl(CardBenefitRepository cardBenefitRepository, CardPerformanceRepository cardPerformanceRepository,
                                  @Value("${financial.card.card-benefit.get-all}") String FINANCIAL_GET_BENEFITS,
                                  CardVersionRepository cardVersionRepository, MerchantInfoRepository merchantInfoRepository) {
        this.cardBenefitRepository = cardBenefitRepository;
        this.cardPerformanceRepository = cardPerformanceRepository;
        this.FINANCIAL_GET_BENEFITS = FINANCIAL_GET_BENEFITS;
        this.restClient = RestClient.create();
        this.cardVersionRepository = cardVersionRepository;
        this.merchantInfoRepository = merchantInfoRepository;
    }

    @Override
    public int createCardBenefitsFromFinancial(){
        String response = restClient.get()
                .uri(FINANCIAL_GET_BENEFITS)
                .retrieve()
                .body(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        FinancialCardBenefitResponses financialCardBenefitResponses;
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");

            financialCardBenefitResponses = objectMapper.treeToValue(dataNode, FinancialCardBenefitResponses.class);
        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", e.getMessage());
            throw new SaveBenefitsFromFinancialException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }

        List<CardBenefit> cardBenefits = financialCardBenefitResponses.getBankCardBenefitGetResponses().stream()
                .map(financialCardBenefitResponse -> {
                    Long cardPerformanceId = financialCardBenefitResponse.getCardPerformanceId();
                    CardPerformance cardPerformance = cardPerformanceRepository.findById(cardPerformanceId);
                    return CardBenefit.of(cardPerformance, financialCardBenefitResponse);
                })
                .toList();

        return cardBenefitRepository.saveAll(cardBenefits).size();
    }

    @Override
    public CardBenefitResponse getCardBenefits(int cardVersionId, int level) {
        // 1. 카드 버전 정보 조회
        CardVersion cardVersion = cardVersionRepository.findById(cardVersionId).get();

        // 2. 카드 버전의 level에 맞는 실적 데이터 조회
        CardPerformance cardPerformance = cardPerformanceRepository.findByCardVersionAndLevel(cardVersion, level).get();

        // 3. 카드 퍼포먼스 ID로 혜택 목록 조회
        List<CardBenefit> cardBenefits = cardBenefitRepository.findByCardPerformance(cardPerformance);


        String cardName = cardVersion.getCardInfo().getName();
        String cardImageUrl = cardVersion.getCardInfo().getCardImage();

        // 4. 혜택을 카테고리별로 그룹화
        Map<String, List<BenefitDetail>> benefitsByCategory = new HashMap<>();
        for (CardBenefit benefit : cardBenefits) {
            String merchantName = merchantInfoRepository.findByMerchantId(benefit.getMerchantId()).getName();

            String discount = buildDiscountString(benefit);

            // 카테고리 별로 혜택을 추가
            benefitsByCategory.computeIfAbsent(benefit.getMerchantCategory(), k -> new ArrayList<>())
                    .add(BenefitDetail.of(merchantName, discount));
        }

        // 5. 카테고리별 응답 데이터 생성
        List<CategoryBenefitResponse> categories = benefitsByCategory.entrySet().stream()
                .map(entry -> CategoryBenefitResponse.of(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // 6. 최종 카드 혜택 응답 데이터 생성
        return CardBenefitResponse.of(cardName,
                cardImageUrl,
                categories);

    }

    private String buildDiscountString(CardBenefit benefit) {
        String discountType = switch (benefit.getBenefitType()) {
            case "PERCENT_DISCOUNT" -> "% 할인";
            case "PERCENT_REWARD" -> "% 적립";
            case "PERCENT_CASHBACK" -> "% 캐시백";
            case "AMOUNT_DISCOUNT", "LITER_DISCOUNT" -> "원 할인";
            case "POINT_REWARD" -> "포인트 적립";
            case "AMOUNT_CASHBACK" -> "원 캐쉬백";
            default -> "Unknown Type";
        };

        if (benefit.getBenefitPer() > 0) {
            if (benefit.getBenefitType().equals("LITER_DISCOUNT")) {
                return String.format("%d리터당 %.1f%s", benefit.getBenefitPer(), benefit.getBenefitValue(), discountType);
            } else {
                return String.format("%d원당 %.1f%s", benefit.getBenefitPer(), benefit.getBenefitValue(), discountType);
            }
        } else {
            return String.format("%.1f%s", benefit.getBenefitValue(), discountType);
        }
    }

}
