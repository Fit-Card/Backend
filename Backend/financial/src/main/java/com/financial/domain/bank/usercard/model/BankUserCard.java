package com.financial.domain.bank.usercard.model;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardSaveRequest;
import com.financial.domain.fin.user.model.FinUser;
import com.financial.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_user_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankUserCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bank_card_id", nullable = false)
    private BankCard bankCard;

    @ManyToOne
    @JoinColumn(name = "fin_user_id", nullable = false)
    private FinUser finUser;

    @NotBlank
    private String globalBrand;

    @NotBlank
    private String expiredDate;

    @NotNull
    private char cardMemberType;

    public BankUserCard(BankCard bankCard, FinUser finUser, String globalBrand, String expiredDate, char cardMemberType) {
        this.bankCard = bankCard;
        this.finUser = finUser;
        this.globalBrand = globalBrand;
        this.expiredDate = expiredDate;
        this.cardMemberType = cardMemberType;
    }

    public static BankUserCard of(BankUserCardSaveRequest request, BankCard bankCard, FinUser finUser) {
        return new BankUserCard(bankCard, finUser, request.getGlobalBrand(), request.getExpiredDate(), request.getCardMemberType());
    }
}
