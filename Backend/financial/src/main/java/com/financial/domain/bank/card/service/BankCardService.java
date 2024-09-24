package com.financial.domain.bank.card.service;

import com.financial.domain.bank.card.model.dto.response.BankCardGetResponses;

public interface BankCardService {

    BankCardGetResponses getCardsByCompany(String cardCompanyId);
}
