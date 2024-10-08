package com.fitcard.domain.card.performance.model;

import com.fitcard.domain.card.performance.model.dto.response.FinancialCardPerformanceResponse;
import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Entity
@Table(name = "card_performance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPerformance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private int level;

    @NotNull
    private int amount;

    @NotNull
    private int benefitLimit;

    @ManyToOne
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion cardVersion;

    private CardPerformance(CardVersion cardVersion, int level, int amount, int benefitLimit) {
        this.cardVersion = cardVersion;
        this.level = level;
        this.amount = amount;
        this.benefitLimit = benefitLimit;
    }

    public static CardPerformance of(CardVersion cardVersion, FinancialCardPerformanceResponse financialCardPerformanceResponse) {
        return new CardPerformance(cardVersion,
                financialCardPerformanceResponse.getLevel(),
                financialCardPerformanceResponse.getAmount(),
                financialCardPerformanceResponse.getBenefitLimit());
    }
}
