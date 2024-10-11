package com.financial.domain.bank.cardevent.repository;

import com.financial.domain.bank.cardbenefit.model.BankCardBenefit;
import com.financial.domain.bank.cardevent.model.BankCardEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardEventRepository extends JpaRepository<BankCardEvent, Long> {

}