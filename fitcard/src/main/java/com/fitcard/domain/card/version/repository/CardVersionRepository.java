package com.fitcard.domain.card.version.repository;

import com.fitcard.domain.card.version.model.CardVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardVersionRepository extends JpaRepository<CardVersion, Integer> {
}
