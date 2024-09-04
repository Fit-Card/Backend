package com.fitcard.domain.event.model;

import com.fitcard.domain.card.company.model.CardCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "card_event")
@Getter
@NoArgsConstructor
public class CardEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardEventId;

    @ManyToOne
    @JoinColumn(name = "card_company_id", nullable = false)
    private CardCompany cardCompanyId;

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

    private CardEvent(CardCompany cardCompanyId, String target, Boolean isCategory, String eventUrl, LocalDate startDate, LocalDate endDate, String title, String content) {
        this.cardCompanyId = cardCompanyId;
        this.target = target;
        this.isCategory = isCategory;
        this.eventUrl = eventUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }
}
