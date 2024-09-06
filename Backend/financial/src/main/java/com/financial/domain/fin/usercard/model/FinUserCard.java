package com.financial.domain.fin.usercard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "fin_user_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinUserCard {

    @Id
    private Integer id;

    @NotBlank
    private String cardNumMasked;

    @NotBlank
    private boolean cardNumberType;

    @JoinColumn(name = "bank_card", nullable = false)
    private String bankCardId;

    @JoinColumn(name = "fin_card_company", nullable = false)
    private String finCardCompanyId;

    @JoinColumn(name = "fin_user", nullable = false)
    private String finUserId;
}
