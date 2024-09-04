package com.fitcard.domain.event.repository;

import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardEventRepository extends JpaRepository<Event, Integer> {
}
