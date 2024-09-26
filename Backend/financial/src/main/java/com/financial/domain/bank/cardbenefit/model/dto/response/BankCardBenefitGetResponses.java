package com.financial.domain.bank.cardbenefit.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardBenefitGetResponses {

    private List<BankCardBenefitGetResponse> bankCardBenefitGetResponses;

    private int size;

    public static BankCardBenefitGetResponses from(List<BankCardBenefitGetResponse> bankCardBenefitGetResponse) {
        return new BankCardBenefitGetResponses(bankCardBenefitGetResponse, bankCardBenefitGetResponse.size());
    }

}
