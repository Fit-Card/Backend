package com.fitcard.domain.card.company.model;

import com.fitcard.domain.card.company.model.dto.response.FinancialCardCompanyGetResponses;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "card_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String financialCardId;

    @NotEmpty
    private String name;

    @Column(length=1000)
    private String imageUrl;

    private CardCompany( String financialCardId, String name, String imageUrl) {
        this.financialCardId = financialCardId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static CardCompany from(FinancialCardCompanyGetResponses.FinancialCardCompanyGetResponse financialCardCompanyGetAllResponse) {
        return new CardCompany(financialCardCompanyGetAllResponse.getCompanyId(),
                financialCardCompanyGetAllResponse.getCompanyName(),
                financialCardCompanyGetAllResponse.getCompanyImageUrl());
    }
}
