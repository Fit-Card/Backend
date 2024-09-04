package com.fitcard.domain.card.performance.model;

import com.fitcard.domain.card.version.model.CardVersion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private int level;

    @NotNull
    private int amount;

    @ManyToOne
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion cardVersion;
}
