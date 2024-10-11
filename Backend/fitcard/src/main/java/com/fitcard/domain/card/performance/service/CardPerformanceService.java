package com.fitcard.domain.card.performance.service;

import com.fitcard.domain.card.performance.model.CardPerformance;

import java.util.List;

public interface CardPerformanceService {

    int createCardPerformancesFromFinancial();

    List<CardPerformance> getCardPerformances(int cardVersionId);
}
