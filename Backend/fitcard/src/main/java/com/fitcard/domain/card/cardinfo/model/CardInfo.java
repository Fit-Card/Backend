package com.fitcard.domain.card.cardinfo.model;

import com.fitcard.domain.card.company.model.CardCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "card_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardInfo {

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
    private String cardCode;

}
