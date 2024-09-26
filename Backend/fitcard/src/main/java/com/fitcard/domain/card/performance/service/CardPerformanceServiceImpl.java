package com.fitcard.domain.card.performance.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.performance.exception.SavePerformancesFromFinancialException;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.model.dto.response.FinancialCardPerformanceResponses;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.card.version.repository.CardVersionRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CardPerformanceServiceImpl implements CardPerformanceService {

    private final CardPerformanceRepository cardPerformanceRepository;
    private final CardVersionRepository cardVersionRepository;
    private final CardInfoRepository cardInfoRepository;
    private final String FINANCIAL_GET_PERFORMANCES;
    private final RestClient restClient;

    public CardPerformanceServiceImpl(CardPerformanceRepository cardPerformanceRepository, CardVersionRepository cardVersionRepository, CardInfoRepository cardInfoRepository, @Value("${financial.card.card-performance.get-all}") String FINANCIAL_GET_PERFORMANCES) {
        this.cardPerformanceRepository = cardPerformanceRepository;
        this.cardVersionRepository = cardVersionRepository;
        this.cardInfoRepository = cardInfoRepository;
        this.FINANCIAL_GET_PERFORMANCES = FINANCIAL_GET_PERFORMANCES;
        this.restClient = RestClient.create();
    }

    @Override
    public int createCardPerformancesFromFinancial(){
        String response = restClient.get()
                .uri(FINANCIAL_GET_PERFORMANCES)
                .retrieve()
                .body(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        FinancialCardPerformanceResponses financialCardPerformanceResponses;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");

            financialCardPerformanceResponses = objectMapper.treeToValue(dataNode, FinancialCardPerformanceResponses.class);
        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", e.getMessage());
            throw new SavePerformancesFromFinancialException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }

        List<CardPerformance> cardPerformances = financialCardPerformanceResponses.getBankCardPerformanceGetResponses().stream()
                .map(financialCardPerformanceResponse -> {
                    CardInfo cardInfo = cardInfoRepository.findByFinancialCardId(financialCardPerformanceResponse.getCardId()).get();
                    CardVersion cardVersion = cardVersionRepository.findByCardInfo(cardInfo);
                    return CardPerformance.of(cardVersion, financialCardPerformanceResponse);
                })
                .toList();
        return cardPerformanceRepository.saveAll(cardPerformances).size();
    }

}
