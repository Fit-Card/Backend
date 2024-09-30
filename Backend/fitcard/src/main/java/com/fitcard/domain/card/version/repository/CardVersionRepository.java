package com.fitcard.domain.card.version.repository;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.version.model.CardVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardVersionRepository extends JpaRepository<CardVersion, Integer> {
    CardVersion findByCardInfo(CardInfo cardInfo);

    @Query("SELECT cv FROM CardVersion cv WHERE cv.cardInfo.id = :cardId ORDER BY cv.version DESC")
    Optional<CardVersion> findTopByCardInfoIdOrderByVersionDesc(@Param("cardId") int cardId);
}
