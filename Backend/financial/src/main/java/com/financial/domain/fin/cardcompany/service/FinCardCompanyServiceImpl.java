package com.financial.domain.fin.cardcompany.service;

import com.financial.domain.fin.cardcompany.model.dto.response.CardCompanyGetResponse;
import com.financial.domain.fin.cardcompany.model.dto.response.CardCompanyGetResponses;
import com.financial.domain.fin.cardcompany.repository.FinCardCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FinCardCompanyServiceImpl implements FinCardCompanyService {

    private final FinCardCompanyRepository finCardCompanyRepository;

    @Override
    public CardCompanyGetResponses getAllCardCompanies() {
        List<CardCompanyGetResponse> cardCompanyGetResponses = finCardCompanyRepository.findAll().stream()
                .map(CardCompanyGetResponse::of)
                .toList();

        return CardCompanyGetResponses.from(cardCompanyGetResponses);
    }
}
