package com.financial.domain.bank.usercardpayment.repository;

import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.bank.usercardpayment.model.BankUserCardPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankUserCardPaymentRepository extends JpaRepository<BankUserCardPayment, Integer> {

    @Query("SELECT b FROM BankUserCardPayment b WHERE b.bankUserCard = :bankUserCard AND b.id > :id AND b.paymentDate < :now")
    List<BankUserCardPayment> findAllByBankUserCardAndIdGreaterThanAndPaymentDateBefore(
            @Param("bankUserCard") BankUserCard bankUserCard,
            @Param("id") Integer id,
            @Param("now") String now);

    List<BankUserCardPayment> findAllByBankUserCardAndIdGreaterThan(BankUserCard bankUserCard, Integer lastId);
}
