package com.fitcard.domain.card.version.model;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "card_version")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "card_info_id", nullable = false)
    private CardInfo cardInfo;

    @NotNull
    private int annualFee;

    @NotEmpty
    private String imageUrl;

    @NotNull
    private int version;

    private boolean isCredit;

    private boolean isBC;

}