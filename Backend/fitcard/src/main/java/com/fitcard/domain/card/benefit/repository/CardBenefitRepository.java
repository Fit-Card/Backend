package com.fitcard.domain.card.benefit.repository;

import com.fitcard.domain.card.benefit.model.CardBenefit;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.performance.model.CardPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardBenefitRepository extends JpaRepository<CardBenefit, Integer> {
    List<CardBenefit> findByCardPerformance(CardPerformance cardPerformance);
}
