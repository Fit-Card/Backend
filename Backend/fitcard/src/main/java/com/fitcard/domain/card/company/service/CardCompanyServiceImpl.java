package com.fitcard.domain.card.company.service;

import com.fitcard.domain.card.company.model.dto.response.CardCompanyGetAllResponses;
import com.fitcard.domain.card.company.model.dto.response.CardCompanyGetResponse;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CardCompanyServiceImpl implements CardCompanyService {

    private final CardCompanyRepository cardCompanyRepository;

    @Override
    public CardCompanyGetAllResponses getAllCardCompany() {
        List<CardCompanyGetResponse> cardCompanyGetResponses = cardCompanyRepository.findAll().stream()
                .map(CardCompanyGetResponse::of)
                .toList();

        return CardCompanyGetAllResponses.from(cardCompanyGetResponses);
    }
}
