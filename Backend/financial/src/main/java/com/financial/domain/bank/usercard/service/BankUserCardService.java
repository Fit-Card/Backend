package com.financial.domain.bank.usercard.service;

import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardDeleteRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardGetRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardSaveRequest;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponses;

public interface BankUserCardService {

    void saveUserCard(BankUserCardSaveRequest request);

    void deleteUserCard(BankUserCardDeleteRequest request);

    BankUserCardGetResponses getAllUserCards(BankUserCardGetRequest request);
}
