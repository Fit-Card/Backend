package com.fitcard.domain.card.performance.repository;

import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.version.model.CardVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardPerformanceRepository extends JpaRepository<CardPerformance, Integer> {
    CardPerformance findById(Long cardPerformanceId);

    Optional<CardPerformance> findByCardVersionAndLevel(CardVersion cardVersion, int level);

    List<CardPerformance> findByCardVersion(CardVersion cardVersion);

    @Query(value = """
            SELECT cp
            FROM CardPerformance cp
            JOIN CardVersion cv ON cp.cardVersion.id = cv.id
            JOIN (
                SELECT cp2.cardVersion.id AS cardVersionId, MAX(cp2.amount) AS maxAmount
                FROM CardPerformance cp2
                WHERE cp2.amount <= :amount
                GROUP BY cp2.cardVersion.id
            ) grouped_cp ON cp.cardVersion.id = grouped_cp.cardVersionId AND cp.amount = grouped_cp.maxAmount
            JOIN (
                SELECT cv2.cardInfo.id AS cardInfoId, MAX(cv2.version) AS maxVersion
                FROM CardVersion cv2
                GROUP BY cv2.cardInfo.id
            ) grouped_cv ON cv.cardInfo.id = grouped_cv.cardInfoId AND cv.version = grouped_cv.maxVersion
            """, nativeQuery = false)
    List<CardPerformance> findNewestVersionAndMaxAmount(@Param("amount") int amount);

    boolean existsByCardVersion(CardVersion cardVersion);
}
