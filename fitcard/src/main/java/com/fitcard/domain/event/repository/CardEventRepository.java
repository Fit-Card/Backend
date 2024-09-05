package com.fitcard.domain.event.repository;

import com.fitcard.domain.event.model.CardEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardEventRepository extends JpaRepository<CardEvent, Integer> {
}
