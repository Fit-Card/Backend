package com.fitcard.domain.alarm.repository;

import com.fitcard.domain.alarm.model.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
}
