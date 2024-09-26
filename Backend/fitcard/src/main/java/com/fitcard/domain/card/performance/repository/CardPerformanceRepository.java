package com.fitcard.domain.card.performance.repository;

import com.fitcard.domain.card.performance.model.CardPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardPerformanceRepository extends JpaRepository<CardPerformance, Integer> {
    CardPerformance findById(Long cardPerformanceId);
}
