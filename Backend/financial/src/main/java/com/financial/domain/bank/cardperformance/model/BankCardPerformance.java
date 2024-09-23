package com.financial.domain.bank.cardperformance.model;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "bank_card_performance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankCardPerformance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardPerformanceId;

    @NotNull
    private int level;

    @NotNull
    private int amount;

    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private BankCard bankCard;

    public BankCardPerformance(BankCard bankCard, int level, int amount) {
        this.bankCard = bankCard;
        this.level = level;
        this.amount = amount;
    }

}
