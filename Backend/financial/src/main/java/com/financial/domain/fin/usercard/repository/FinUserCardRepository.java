package com.financial.domain.fin.usercard.repository;

import com.financial.domain.fin.usercard.model.FinUserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinUserCardRepository extends JpaRepository<FinUserCard, Long> {
}
