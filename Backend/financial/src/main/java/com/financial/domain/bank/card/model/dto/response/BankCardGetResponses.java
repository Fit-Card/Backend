package com.financial.domain.bank.card.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardGetResponses {

    private List<BankCardGetResponse> bankCardGetResponses;

    private int size;

    public static BankCardGetResponses from(List<BankCardGetResponse> bankCardGetResponse) {
        return new BankCardGetResponses(bankCardGetResponse, bankCardGetResponse.size());
    }
}