package com.fitcard.domain.card.company.repository;

import com.fitcard.domain.card.company.model.CardCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCompanyRepository extends JpaRepository<CardCompany, Integer> {
}
