package com.financial.domain.bank.usercard.model;

import com.financial.global.common.BaseEntity;
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
@Table(name = "bank_user_card")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankUserCard extends BaseEntity {

    @Id
    private Integer id;

    @JoinColumn(name = "bank_card", nullable = false)
    private String bankCardId;

    @NotBlank
    private String globalBrand;

    @NotBlank
    private String expiredDate;
}
