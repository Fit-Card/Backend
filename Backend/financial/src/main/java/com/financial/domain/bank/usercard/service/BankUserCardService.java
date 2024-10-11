package com.financial.domain.bank.usercard.service;

import com.financial.domain.bank.usercard.model.dto.request.BankUserCardDeleteRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardGetAllRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardGetRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardSaveRequest;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponse;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponses;

public interface BankUserCardService {

    void createUserCard(BankUserCardSaveRequest request);

    void deleteUserCard(BankUserCardDeleteRequest request);

    BankUserCardGetResponses getAllUserCards(BankUserCardGetAllRequest request);

    BankUserCardGetResponse getUserCard(BankUserCardGetRequest request);
}
