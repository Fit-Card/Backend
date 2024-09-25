package com.financial.domain.bank.usercard.model.dto.response;

import com.financial.domain.fin.cardcompany.model.dto.response.CardCompanyGetResponse;
import com.financial.domain.fin.cardcompany.model.dto.response.CardCompanyGetResponses;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankUserCardGetResponses {

    private List<BankUserCardGetResponse> bankUserCardGetResponses;

    private int size;

    public static BankUserCardGetResponses from(List<BankUserCardGetResponse> bankUserCardGetResponses) {
        return new BankUserCardGetResponses(bankUserCardGetResponses, bankUserCardGetResponses.size());
    }
}
