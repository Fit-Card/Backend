package com.fitcard.domain.event.model;

import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.event.model.dto.response.FinancialCardEventResponse;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "card_event")
@Getter
@NoArgsConstructor
public class CardEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardEventId;

    @ManyToOne
    @JoinColumn(name = "card_company_id", nullable = false)
    private CardCompany cardCompany;

    @NotEmpty
    private String target;

    @NotNull
    private Boolean isCategory;

    @NotEmpty
    private String eventUrl;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private boolean isCredit;

    @NotNull
    private boolean isBC;

    @NotNull
    private Boolean isPersonal;

    private CardEvent(CardCompany cardCompany, String target, Boolean isCategory, String eventUrl, LocalDate startDate, LocalDate endDate, String title, String content, boolean isCredit, boolean isBC, Boolean isPersonal) {
        this.cardCompany = cardCompany;
        this.target = target;
        this.isCategory = isCategory;
        this.eventUrl = eventUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
        this.isCredit = isCredit;
        this.isBC = isBC;
        this.isPersonal = isPersonal;
    }

    public static CardEvent of(CardCompany cardCompany, FinancialCardEventResponse financialCardEventResponse, String target, Boolean isCategory, Boolean isBC, Boolean isPersonal, Boolean isCredit) {
        return new CardEvent(cardCompany,
                target,
                isCategory,
                financialCardEventResponse.getEventUrl(),
                financialCardEventResponse.getStartDate(),
                financialCardEventResponse.getEndDate(),
                financialCardEventResponse.getTitle(),
                financialCardEventResponse.getContent(),
                isCredit,
                isBC,
                isPersonal);
    }
}
