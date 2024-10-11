package com.financial.domain.bank.usercardpayment.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BankUserCardPaymentGetRequest {

    @NotNull
    private Long bankUserCardId;

    @NotNull
    private Integer lastId;
}
