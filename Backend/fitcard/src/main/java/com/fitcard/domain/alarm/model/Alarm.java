package com.fitcard.domain.alarm.model;

import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.event.model.CardEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "card_event_id", nullable = false)
    private CardEvent cardEventId;

    @ManyToOne
    @JoinColumn(name = "card_company_id", nullable = false)
    private CardCompany cardCompanyId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    // private 생성자
    private Alarm(CardEvent cardEventId, CardCompany cardCompanyId, String title, String content) {
        this.cardEventId = cardEventId;
        this.cardCompanyId = cardCompanyId;
        this.title = title;
        this.content = content;
    }
}
