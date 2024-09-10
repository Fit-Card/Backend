package com.financial.domain.bank.card.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankCard {

    @Id
    private String cardId;

    @NotBlank
    private String cardName;

    @NotNull
    private Integer annualFee;

    @NotNull
    private Integer abroadAnnualFee;

    @NotNull
    private boolean createCheckType;

    @NotNull
    private boolean isBC;

    @NotNull
    private String cardImageUrl;

    public BankCard(String cardId, String cardName, Integer annualFee, Integer abroadAnnualFee, boolean createCheckType, boolean isBC, String cardImageUrl) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.annualFee = annualFee;
        this.abroadAnnualFee = abroadAnnualFee;
        this.createCheckType = createCheckType;
        this.isBC = isBC;
        this.cardImageUrl = cardImageUrl;
    }
}
