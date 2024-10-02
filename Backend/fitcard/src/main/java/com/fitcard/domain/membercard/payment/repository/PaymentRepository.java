package com.fitcard.domain.membercard.payment.repository;

import com.fitcard.domain.membercard.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

//    List<Payment> findAllByMemberCard(MemberCardInfo memberCardInfo);

    boolean existsByFinancialMemberCardPaymentId(Integer financialMemberCardPaymentId);
}
