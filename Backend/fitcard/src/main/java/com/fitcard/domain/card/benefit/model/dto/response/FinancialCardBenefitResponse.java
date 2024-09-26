package com.fitcard.domain.card.benefit.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fitcard.domain.card.benefit.model.BenefitType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class FinancialCardBenefitResponse {

    @NotNull
    private Long cardBenefitId;

    @NotEmpty
    private Long cardPerformanceId;

    @NotNull
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
}
