package com.financial.domain.bank.usercardpayment.repository;

import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.bank.usercardpayment.model.BankUserCardPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankUserCardPaymentRepository extends JpaRepository<BankUserCardPayment, Integer> {

    List<BankUserCardPayment> findAllByBankUserCardAndIdGreaterThan(BankUserCard bankUserCard, Integer lastId);
}
