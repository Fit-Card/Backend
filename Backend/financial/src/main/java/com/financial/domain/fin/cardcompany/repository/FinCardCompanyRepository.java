package com.financial.domain.fin.cardcompany.repository;

import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinCardCompanyRepository extends JpaRepository<FinCardCompany, Integer> {
}
