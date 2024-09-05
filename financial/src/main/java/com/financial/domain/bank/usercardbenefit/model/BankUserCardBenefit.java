package com.financial.domain.bank.usercardbenefit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_user_card_benefit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankUserCardBenefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "bank_user_card", nullable = false)
    private Integer bankUserCardId;

    @JoinColumn(name="bank_user_card_payment", nullable = false)
    private Integer bankUserCardPayment;

    @NotBlank
    private boolean pointCashType;

    @NotBlank
    private Integer benefitValue;

}
