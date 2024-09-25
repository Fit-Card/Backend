package com.fitcard.domain.card.company.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.model.dto.response.CardCompanyGetAllResponses;
import com.fitcard.domain.card.company.model.dto.response.CardCompanyGetResponse;
import com.fitcard.domain.card.company.model.dto.response.FinancialCardCompanyGetResponses;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import com.fitcard.global.error.BusinessException;
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
public class CardCompanyServiceImpl implements CardCompanyService {

    private final CardCompanyRepository cardCompanyRepository;
    private final RestClient restClient;
    private final String FINANCIAL_GET_CARD_COMPANIES;

    public CardCompanyServiceImpl(CardCompanyRepository cardCompanyRepository
            , @Value("${financial.card.card-company.get-all}") String financialCardCompanyGetAll) {
        this.cardCompanyRepository = cardCompanyRepository;
        this.restClient = RestClient.create();
        this.FINANCIAL_GET_CARD_COMPANIES = financialCardCompanyGetAll;
    }

    @Override
    public CardCompanyGetAllResponses getAllCardCompany() {
        List<CardCompanyGetResponse> cardCompanyGetResponses = cardCompanyRepository.findAll().stream()
                .map(CardCompanyGetResponse::of)
                .toList();

        return CardCompanyGetAllResponses.from(cardCompanyGetResponses);
    }

    @Override
    public int createAllCardCompanies() {
        String response = restClient.post()
                .uri(FINANCIAL_GET_CARD_COMPANIES)
                .retrieve()
                .body(String.class);

        //todo: json parsing
        ObjectMapper objectMapper = new ObjectMapper();
        FinancialCardCompanyGetResponses cardCompanyResponses;

        try {
            // JSON 응답을 Response<FinancialCardCompanyGetAllResponses>로 변환
            JsonNode jsonNode = objectMapper.readTree(response);

            // 3. data 부분 추출
            JsonNode dataNode = jsonNode.get("data");

            // 4. data를 FinancialCardCompanyGetResponses로 변환
            cardCompanyResponses = objectMapper.treeToValue(dataNode, FinancialCardCompanyGetResponses.class);
        } catch (Exception e) {
            log.error("Error while parsing JSON response: {}", e.getMessage());
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }

        List<CardCompany> cardCompanies = cardCompanyResponses.getCardCompanies().stream()
                .map(CardCompany::from)
                .toList();

        return cardCompanyRepository.saveAll(cardCompanies).size();
    }
}
