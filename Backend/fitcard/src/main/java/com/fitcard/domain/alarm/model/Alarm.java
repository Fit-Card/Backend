package com.fitcard.domain.alarm.model;

import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.event.model.CardEvent;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarm")
@Getter
@NoArgsConstructor
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "card_event_id", nullable = false)
    private CardEvent cardEvent;

    @ManyToOne
    @JoinColumn(name = "card_company_id", nullable = false)
    private CardCompany cardCompany;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    // private 생성자
    private Alarm(CardEvent cardEvent, CardCompany cardCompany, String title, String content) {
        this.cardEvent = cardEvent;
        this.cardCompany = cardCompany;
        this.title = title;
        this.content = content;
    }

    public static Alarm of(CardEvent cardEvent) {
        return new Alarm(cardEvent, cardEvent.getCardCompany(), cardEvent.getTitle(), cardEvent.getContent());
    }
}
