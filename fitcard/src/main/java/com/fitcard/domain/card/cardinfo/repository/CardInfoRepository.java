package com.fitcard.domain.card.cardinfo.repository;


import com.fitcard.domain.card.cardinfo.model.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardInfoRepository extends JpaRepository<CardInfo, Integer> {
}
