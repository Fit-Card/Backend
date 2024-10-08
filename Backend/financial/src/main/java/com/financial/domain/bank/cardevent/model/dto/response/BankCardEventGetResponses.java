package com.financial.domain.bank.cardevent.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardEventGetResponses {

    private List<BankCardEventGetResponse> bankCardEventGetResponses;

    private int size;

    public static BankCardEventGetResponses from(List<BankCardEventGetResponse> bankCardEventGetResponses) {
        return new BankCardEventGetResponses(bankCardEventGetResponses, bankCardEventGetResponses.size());
    }
}
