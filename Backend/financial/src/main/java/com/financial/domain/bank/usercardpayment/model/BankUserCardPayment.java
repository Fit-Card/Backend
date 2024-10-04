package com.financial.domain.bank.usercardpayment.model;

import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_user_card_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankUserCardPayment extends BaseEntity {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bank_user_card_id", nullable = false)
    private BankUserCard bankUserCard;

    @NotBlank
    private Integer amount;

    @NotBlank
    private String paymentDate;

    @NotBlank
    private String paymentName;

    @NotBlank
    private String paymentCategory;
}
