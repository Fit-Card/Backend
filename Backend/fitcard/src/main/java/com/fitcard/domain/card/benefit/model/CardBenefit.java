package com.fitcard.domain.card.benefit.model;

import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "card_benefit", schema = "fitcard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardBenefit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
    private String exceptionTypes; //todo: exceptionType Enum 만들고 string -> list list -> string convertor 만들어야 한다.

    @ManyToOne
    @JoinColumn(name = "card_performance_id", nullable = false)
    private CardPerformance cardPerformance;

}
