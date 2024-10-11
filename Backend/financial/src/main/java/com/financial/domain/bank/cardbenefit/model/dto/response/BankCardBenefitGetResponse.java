package com.financial.domain.bank.cardbenefit.model.dto.response;

import com.financial.domain.bank.cardbenefit.model.BankCardBenefit;
import com.financial.domain.bank.cardbenefit.model.BenefitType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardBenefitGetResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long cardBenefitId;

    private Long cardPerformanceId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;

    @NotNull
    private String amountLimit;

    @NotEmpty
    private String countLimit;

    @NotNull
    private int minPayment;

    @NotNull
    private double benefitValue;

    @NotNull
    private int benefitPer;

    @NotNull
    private int merchantId;

    @NotEmpty
    private String exceptionTypeList;

    @NotNull
    private String merchantCategory;

    @NotNull
    private String updatedAt;

    public static BankCardBenefitGetResponse of(BankCardBenefit benefit) {
        return new BankCardBenefitGetResponse(
                benefit.getCardBenefitId(),
                benefit.getBankCardPerformance().getCardPerformanceId(),
                benefit.getBenefitType(),
                benefit.getAmountLimit(),
                benefit.getCountLimit(),
                benefit.getMinPayment(),
                benefit.getBenefitValue(),
                benefit.getBenefitPer(),
                benefit.getMerchantId(),
                benefit.getExceptionTypeList(),
                benefit.getMerchantCategory(),
                benefit.getUpdatedAt().format(formatter));
    }


}
