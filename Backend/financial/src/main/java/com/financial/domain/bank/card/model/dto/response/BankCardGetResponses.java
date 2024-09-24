package com.financial.domain.bank.card.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardGetResponses {

    private List<BankCardGetResponse> alarmResponses;

    private int size;

    public static BankCardGetResponses from(List<BankCardGetResponse> bankCardGetRespons) {
        return new BankCardGetResponses(bankCardGetRespons, bankCardGetRespons.size());
    }
}