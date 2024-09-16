package com.financial.domain.bank.cardperformance.repository;

import com.financial.domain.bank.cardperformance.model.BankCardPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardPerformanceRepository extends JpaRepository<BankCardPerformance, Long> {

}
