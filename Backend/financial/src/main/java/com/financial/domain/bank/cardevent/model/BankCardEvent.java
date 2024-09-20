package com.financial.domain.bank.cardevent.model;

import com.financial.domain.bank.cardbenefit.model.BenefitType;
import com.financial.domain.bank.cardperformance.model.BankCardPerformance;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_card_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankCardEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardBenefitId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;

    @NotNull
    private int amountLimit;

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

    @ManyToOne
    @JoinColumn(name = "card_performance_id", nullable = false)
    private BankCardPerformance bankCardPerformance;

    public BankCardEvent(BankCardPerformance bankCardPerformance, BenefitType benefitType, int amountLimit,
                         String countLimit, int minPayment, double benefitValue, int benefitPer,
                         String merchantCategory, int merchantId, String exceptionTypeList) {
        this.bankCardPerformance = bankCardPerformance;
        this.benefitType = benefitType;
        this.amountLimit = amountLimit;
        this.countLimit = countLimit;
        this.minPayment = minPayment;
        this.benefitValue = benefitValue;
        this.benefitPer = benefitPer;
        this.merchantCategory = merchantCategory;
        this.merchantId = merchantId;
        this.exceptionTypeList = exceptionTypeList;
    }
}
