package com.fitcard.domain.card.benefit.model;

import com.fitcard.domain.card.benefit.model.dto.response.FinancialCardBenefitResponse;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "card_benefit", schema = "fitcard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardBenefit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String benefitType;

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
    private Long merchantId;

    @NotEmpty
    private String exceptionTypes; //todo: exceptionType Enum 만들고 string -> list list -> string convertor 만들어야 한다.

    @NotEmpty
    private String merchantCategory;

    @ManyToOne
    @JoinColumn(name = "card_performance_id", nullable = false)
    private CardPerformance cardPerformance;

    private CardBenefit(CardPerformance cardPerformance, BenefitType benefitType, String amountLimit, String countLimit,
                        int minPayment, double benefitValue, int benefitPer, Long merchantId, String exceptionTypes, String merchantCategory) {
        this.cardPerformance = cardPerformance;
        this.benefitType = benefitType.name();
        this.amountLimit = amountLimit;
        this.countLimit = countLimit;
        this.minPayment = minPayment;
        this.benefitValue = benefitValue;
        this.benefitPer = benefitPer;
        this.merchantId = merchantId;
        this.exceptionTypes = exceptionTypes;
        this.merchantCategory = merchantCategory;
    }

    public static CardBenefit of(CardPerformance cardPerformance, FinancialCardBenefitResponse financialCardBenefitResponse) {
        return new CardBenefit(cardPerformance,
                financialCardBenefitResponse.getBenefitType(),
                financialCardBenefitResponse.getAmountLimit(),
                financialCardBenefitResponse.getCountLimit(),
                financialCardBenefitResponse.getMinPayment(),
                financialCardBenefitResponse.getBenefitValue(),
                financialCardBenefitResponse.getBenefitPer(),
                financialCardBenefitResponse.getMerchantId(),
                financialCardBenefitResponse.getExceptionTypeList(),
                financialCardBenefitResponse.getMerchantCategory());
    }
}
