package com.fitcard.domain.card.benefit.repository;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBenefitRepository extends JpaRepository<CardInfo, Integer> {
}
