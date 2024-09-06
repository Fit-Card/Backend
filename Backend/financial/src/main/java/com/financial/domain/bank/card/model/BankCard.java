package com.financial.domain.bank.card.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankCard {

    @Id
    private String Id;

    @NotBlank
    private String cardName;

    @NotBlank
    private Integer annualFee;

    @NotBlank
    private boolean createCheckType;

    @NotBlank
    private boolean isBC;
}
