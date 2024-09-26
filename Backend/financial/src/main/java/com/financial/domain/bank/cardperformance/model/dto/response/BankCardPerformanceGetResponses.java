package com.financial.domain.bank.cardperformance.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardPerformanceGetResponses {
    private List<BankCardPerformanceGetResponse> bankCardPerformanceGetResponses;

    private int size;

    public static BankCardPerformanceGetResponses from(List<BankCardPerformanceGetResponse> bankCardPerformanceGetResponse) {
        return new BankCardPerformanceGetResponses(bankCardPerformanceGetResponse, bankCardPerformanceGetResponse.size());
    }
}
