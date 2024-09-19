package com.financial.domain.bank.cardbenefit.repository;

import com.financial.domain.bank.cardbenefit.model.BankCardBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardBenefitRepository extends JpaRepository<BankCardBenefit, Long> {

}
