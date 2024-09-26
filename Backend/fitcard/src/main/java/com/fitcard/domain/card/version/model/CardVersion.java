package com.fitcard.domain.card.version.model;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.cardinfo.model.dto.response.FinancialCardInfoResponse;
import com.fitcard.global.common.BaseEntity;
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
public class CardVersion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "card_info_id", nullable = false)
    private CardInfo cardInfo;

    @NotNull
    private int annualFee;

    @NotNull
    private int abroadAnnualFee;

    @NotEmpty
    private String imageUrl;

    @NotNull
    private int version;

    private CardVersion(CardInfo cardInfo, int annualFee, int abroadAnnualFee, String imageUrl, int version) {
        this.cardInfo = cardInfo;
        this.annualFee = annualFee;
        this.abroadAnnualFee = abroadAnnualFee;
        this.imageUrl = imageUrl;
        this.version = version;
    }

    public static CardVersion of(CardInfo cardInfo, FinancialCardInfoResponse financialCardInfoResponse, int version) {
        return new CardVersion(cardInfo,
                financialCardInfoResponse.getAnnualFee(),
                financialCardInfoResponse.getAbroadAnnualFee(),
                financialCardInfoResponse.getCardImageUrl(),
                version);

    }
}