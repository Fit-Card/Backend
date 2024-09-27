package com.fitcard.domain.card.performance.repository;

import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.version.model.CardVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardPerformanceRepository extends JpaRepository<CardPerformance, Integer> {
    CardPerformance findById(Long cardPerformanceId);

    Optional<CardPerformance> findByCardVersionAndLevel(CardVersion cardVersion, int level);
}
