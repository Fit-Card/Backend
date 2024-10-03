package com.fitcard.domain.card.cardinfo.model;

import com.fitcard.domain.card.cardinfo.model.dto.response.FinancialCardInfoResponse;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "card_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "card_company_id", nullable = false)
    private CardCompany cardCompany;

    private String cardImage;

    @NotEmpty
    private String financialCardId;

    private boolean isCredit;

    private boolean isBC;

    private boolean performanceAdded;

    private CardInfo(String name, CardCompany cardCompany, String cardImage, String financialCardId, boolean isCredit, boolean isBC) {
        this.name = name;
        this.cardCompany = cardCompany;
        this.cardImage = cardImage;
        this.financialCardId = financialCardId;
        this.isCredit = isCredit;
        this.isBC = isBC;
    }

    public static CardInfo of(CardCompany cardCompany, FinancialCardInfoResponse financialCardInfoResponse) {
        return new CardInfo(financialCardInfoResponse.getCardName(),
                cardCompany,
                financialCardInfoResponse.getCardImageUrl(),
                financialCardInfoResponse.getCardId(),
                financialCardInfoResponse.isCreateCheckType(),
                financialCardInfoResponse.isBC());
    }
}
