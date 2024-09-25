package com.financial.domain.bank.cardevent.model;

import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "bank_card_event")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankCardEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardEventId;

    @ManyToOne
    @JoinColumn(name="card_company_id")
    private FinCardCompany finCardCompany;

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

    private BankCardEvent(FinCardCompany finCardCompany, String target, Boolean isCategory, String eventUrl, LocalDate startDate, LocalDate endDate, String title, String content) {
        this.finCardCompany = finCardCompany;
        this.target = target;
        this.isCategory = isCategory;
        this.eventUrl = eventUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }
}
