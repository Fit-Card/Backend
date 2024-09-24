package com.financial.domain.bank.card.service;

import com.financial.domain.bank.card.model.dto.response.BankCardGetResponse;
import com.financial.domain.bank.card.model.dto.response.BankCardGetResponses;
import com.financial.domain.bank.card.repository.BankCardRepository;
import com.financial.domain.fin.cardcompany.model.FinCardCompany;
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
public class BankCardServiceImpl implements BankCardService{

    private final BankCardRepository bankCardRepository;
    private final FinCardCompanyRepository finCardCompanyRepository;

    @Override
    public BankCardGetResponses getCardsByCompany(String cardCompanyId) {
        FinCardCompany cardCompany = finCardCompanyRepository.findById(cardCompanyId).orElseThrow();
        List<BankCardGetResponse> bankCardGetResponses = bankCardRepository.findByFinCardCompany(cardCompany).stream()
                .map(BankCardGetResponse::of)
                .toList();

        return BankCardGetResponses.from(bankCardGetResponses);
    }

    @Override
    public BankCardGetResponses getCards() {
        List<BankCardGetResponse> bankCardGetResponses = bankCardRepository.findAll().stream()
                .map(BankCardGetResponse::of)
                .toList();

        return BankCardGetResponses.from(bankCardGetResponses);
    }
}
