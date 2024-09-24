package com.financial.domain.bank.card.model.dto.response;

import com.financial.domain.bank.card.model.BankCard;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardGetResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String cardId;

    @NotEmpty
    private String cardCompanyId;

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
    private String updatedAt;


    public static BankCardGetResponse of(BankCard card) {
        return new BankCardGetResponse(card.getCardId(),
                card.getFinCardCompany().getId(),
                card.getCardName(),
                card.getAnnualFee(),
                card.getAbroadAnnualFee(),
                card.isCreateCheckType(),
                card.isBC(),
                card.getCardImageUrl(),
                card.getUpdatedAt().format(formatter));
    }

}