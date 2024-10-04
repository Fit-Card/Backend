package com.fitcard.domain.card.cardinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.cardinfo.exception.GetCardsByCompanyException;
import com.fitcard.domain.card.cardinfo.exception.SaveCardsFromFinancialException;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.cardinfo.model.dto.response.CardInfoGetResponse;
import com.fitcard.domain.card.cardinfo.model.dto.response.CardInfoGetResponses;
import com.fitcard.domain.card.cardinfo.model.dto.response.FinancialCardInfoResponses;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
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
public class CardInfoServiceImpl implements CardInfoService {

    private final CardInfoRepository cardInfoRepository;
    private final CardCompanyRepository cardCompanyRepository;
    private final String FINANCIAL_GET_CARDS;
    private final RestClient restClient;

    public CardInfoServiceImpl(CardInfoRepository cardInfoRepository, CardCompanyRepository cardCompanyRepository,
                               @Value("${financial.card.card-info.get-all}") String FINANCIAL_GET_CARDS) {
        this.cardInfoRepository = cardInfoRepository;
        this.cardCompanyRepository = cardCompanyRepository;
        this.FINANCIAL_GET_CARDS = FINANCIAL_GET_CARDS;
        this.restClient = RestClient.create();
    }

    @Override
    public CardInfoGetResponses getCardsByCompany(int cardCompanyId) {

        CardCompany cardCompany = cardCompanyRepository.findById(cardCompanyId).orElseThrow(
                () -> new GetCardsByCompanyException(ErrorCode.CARD_COMPANY_NOT_FOUND, "해당하는 카드사가 없습니다."));
        List<CardInfoGetResponse> cardInfoGetResponses = cardInfoRepository.findByCardCompanyAndPerformanceAddedTrue(cardCompany).stream()
                .map(CardInfoGetResponse::of)
                .toList();

        return CardInfoGetResponses.from(cardInfoGetResponses);
    }

    @Override
    public int createCardsFromFinancial() {

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
//            cardCompanyResponses = objectMapper.treeToValue(dataNode, FinancialCardCompanyGetResponses.class);
        } catch (Exception e) {
            log.error("json 파싱 실패!! {}", e.getMessage());
            log.error("{}", e.getStackTrace());
            throw new SaveCardsFromFinancialException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }

        List<CardInfo> cardInfos = financialCardInfoResponses.getBankCardGetResponses().stream()
                .filter(financialCardInfoResponse ->{
                            log.info("card company: {}", financialCardInfoResponse.getCardCompanyId());
                            return !cardInfoRepository.existsCardInfoByFinancialCardId(financialCardInfoResponse.getCardId())
                                    && cardCompanyRepository.existsByFinancialCardId(financialCardInfoResponse.getCardCompanyId());
                })
                .map(financialCardInfoResponse -> {
//                    log.info("있다!!");
                    CardCompany cardCompany = cardCompanyRepository.findByFinancialCardId(financialCardInfoResponse.getCardCompanyId()).get();
                    return CardInfo.of(cardCompany, financialCardInfoResponse);
                })
                .toList();

        return cardInfoRepository.saveAll(cardInfos).size();
    }
}
