package com.fitcard.domain.card.company.repository;

import com.fitcard.domain.card.company.model.CardCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardCompanyRepository extends JpaRepository<CardCompany, Integer> {
}
