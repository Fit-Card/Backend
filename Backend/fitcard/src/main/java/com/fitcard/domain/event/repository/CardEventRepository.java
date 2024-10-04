package com.fitcard.domain.event.repository;

import com.fitcard.domain.event.model.CardEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CardEventRepository extends JpaRepository<CardEvent, Integer> {

    @Query("SELECT e FROM CardEvent e WHERE e.createdAt > :cutoffTime")
    List<CardEvent> findNewEvents(LocalDateTime cutoffTime);

}
