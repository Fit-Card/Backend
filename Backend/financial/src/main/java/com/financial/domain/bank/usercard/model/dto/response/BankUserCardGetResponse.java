package com.financial.domain.bank.usercard.model.dto.response;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.fin.user.model.FinUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankUserCardGetResponse {

    @NotNull
    private int bankUserCardId;

    @NotBlank
    private String bankCardId;

    @NotBlank
    private String finUserId;

    @NotBlank
    private String globalBrand;

    @NotBlank
    private String expiredDate;

    @NotNull
    private char cardMemberType;

    @NotBlank
    private String finCardCompanyId;

    public static BankUserCardGetResponse from(BankUserCard bankUserCard) {
        return new BankUserCardGetResponse(bankUserCard.getId(),
                bankUserCard.getBankCard().getCardId(),
                bankUserCard.getFinUser().getId(),
                bankUserCard.getGlobalBrand(),
                bankUserCard.getExpiredDate(),
                bankUserCard.getCardMemberType(),
                bankUserCard.getBankCard().getFinCardCompany().getId());
    }
}
