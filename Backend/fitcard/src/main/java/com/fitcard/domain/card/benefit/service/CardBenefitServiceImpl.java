package com.fitcard.domain.card.benefit.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.benefit.exception.SaveBenefitsFromFinancialException;
import com.fitcard.domain.card.benefit.model.CardBenefit;
import com.fitcard.domain.card.benefit.model.dto.response.FinancialCardBenefitResponse;
import com.fitcard.domain.card.benefit.model.dto.response.FinancialCardBenefitResponses;
import com.fitcard.domain.card.benefit.repository.CardBenefitRepository;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.performance.exception.SavePerformancesFromFinancialException;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.model.dto.response.FinancialCardPerformanceResponses;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.card.version.repository.CardVersionRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CardBenefitServiceImpl implements CardBenefitService {

    private final CardBenefitRepository cardBenefitRepository;
    private final CardPerformanceRepository cardPerformanceRepository;
    private final String FINANCIAL_GET_BENEFITS;
    private final RestClient restClient;

    public CardBenefitServiceImpl(CardBenefitRepository cardBenefitRepository, CardPerformanceRepository cardPerformanceRepository, @Value("${financial.card.card-benefit.get-all}") String FINANCIAL_GET_BENEFITS) {
        this.cardBenefitRepository = cardBenefitRepository;
        this.cardPerformanceRepository = cardPerformanceRepository;
        this.FINANCIAL_GET_BENEFITS = FINANCIAL_GET_BENEFITS;
        this.restClient = RestClient.create();
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

}
