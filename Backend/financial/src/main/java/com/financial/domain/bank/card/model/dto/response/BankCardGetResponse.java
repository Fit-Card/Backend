package com.financial.domain.bank.card.model.dto.response;

import com.financial.domain.bank.card.model.BankCard;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardGetResponse {

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

    @NotNull
    private LocalDateTime updatedAt;


    public static BankCardGetResponse of(BankCard card) {
        return new BankCardGetResponse(card.getCardId(),
                card.getCardName(),
                card.getAnnualFee(),
                card.getAbroadAnnualFee(),
                card.isCreateCheckType(),
                card.isBC(),
                card.getCardImageUrl(),
                card.getUpdatedAt());
    }

}