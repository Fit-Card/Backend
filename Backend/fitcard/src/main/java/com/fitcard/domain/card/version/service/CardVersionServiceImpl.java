package com.fitcard.domain.card.version.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.cardinfo.exception.SaveCardsFromFinancialException;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.cardinfo.model.dto.response.FinancialCardInfoResponses;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.card.version.repository.CardVersionRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CardVersionServiceImpl implements CardVersionService {

    private final CardInfoRepository cardInfoRepository;
    private final CardCompanyRepository cardCompanyRepository;
    private final CardVersionRepository cardVersionRepository;
    private final String FINANCIAL_GET_CARDS;
    private final RestClient restClient;

    public CardVersionServiceImpl(CardInfoRepository cardInfoRepository, CardVersionRepository cardVersionRepository,
                                  CardCompanyRepository cardCompanyRepository, @Value("${financial.card.card-info.get-all}") String FINANCIAL_GET_CARDS) {
        this.cardInfoRepository = cardInfoRepository;
        this.cardCompanyRepository = cardCompanyRepository;
        this.cardVersionRepository = cardVersionRepository;
        this.FINANCIAL_GET_CARDS = FINANCIAL_GET_CARDS;
        this.restClient = RestClient.create();
    }

    @Override
    public int createCardVersionsFromFinancial() {

        String response = restClient.get()
                .uri(FINANCIAL_GET_CARDS)
                .retrieve()
                .body(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        FinancialCardInfoResponses financialCardInfoResponses;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");
//            log.info("data size: {}", dataNode.get("size"));
            financialCardInfoResponses = objectMapper.treeToValue(dataNode, FinancialCardInfoResponses.class);
        } catch (Exception e) {
            log.error("json 파싱 실패: {}", e.getMessage());
            throw new SaveCardsFromFinancialException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }

        List<CardVersion> cardVersions = financialCardInfoResponses.getBankCardGetResponses().stream()
                .filter(financialCardInfoResponse -> {
//                    log.info("card company: {}", financialCardInfoResponse.getCardCompanyId());

                    // 카드 정보가 존재하는지 확인
                    return cardInfoRepository.existsCardInfoByFinancialCardId(financialCardInfoResponse.getCardId())
                            && cardCompanyRepository.existsByFinancialCardId(financialCardInfoResponse.getCardCompanyId());
                })
                .map(financialCardInfoResponse -> {
//                    log.info("여기!");
                    // 카드 정보를 가져오기
                    CardInfo cardInfo = cardInfoRepository.findByFinancialCardId(financialCardInfoResponse.getCardId()).get();

                    // 새로운 CardVersion 생성 (버전은 1로 설정)
                    return CardVersion.of(cardInfo, financialCardInfoResponse, 1);
                })
                .toList();

        // 변환된 CardVersion 리스트를 데이터베이스에 저장
        return cardVersionRepository.saveAll(cardVersions).size();
    }


}
