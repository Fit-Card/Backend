package com.fitcard.domain.event.service;

import com.fitcard.domain.event.model.CardEvent;

import java.util.List;

public interface CardEventService {

    boolean detectNewCardEvents();

    void sendNotificationForNewEvents(List<CardEvent> newCardEvents);

    int createCardEventsFromFinancial();
}
