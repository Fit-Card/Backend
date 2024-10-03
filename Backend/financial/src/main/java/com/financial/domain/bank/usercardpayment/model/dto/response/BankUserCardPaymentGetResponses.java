package com.financial.domain.bank.usercardpayment.model.dto.response;

import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankUserCardPaymentGetResponses {

    private List<BankUserCardPaymentGetResponse> bankUserCardPaymentGetResponses;

    private int size;

    public static BankUserCardPaymentGetResponses from(List<BankUserCardPaymentGetResponse> bankUserCardPaymentGetResponses) {
        return new BankUserCardPaymentGetResponses(bankUserCardPaymentGetResponses, bankUserCardPaymentGetResponses.size());
    }
}
