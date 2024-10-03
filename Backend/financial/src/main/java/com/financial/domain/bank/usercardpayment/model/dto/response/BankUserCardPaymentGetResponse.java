package com.financial.domain.bank.usercardpayment.model.dto.response;

import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.bank.usercardpayment.model.BankUserCardPayment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankUserCardPaymentGetResponse {

    @NotNull
    private Integer id;

    @NotBlank
    private Integer amount;

    @NotBlank
    private String paymentDate;

    @NotBlank
    private String paymentName;

    @NotBlank
    private String paymentCategory;

    public static BankUserCardPaymentGetResponse from(BankUserCardPayment bankUserCardPayment) {
        return new BankUserCardPaymentGetResponse(
                bankUserCardPayment.getId(),
                bankUserCardPayment.getAmount(),
                bankUserCardPayment.getPaymentDate(),
                bankUserCardPayment.getPaymentName(),
                bankUserCardPayment.getPaymentCategory());
    }
}
