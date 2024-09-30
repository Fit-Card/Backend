package com.fitcard.domain.membercard.payment.repository;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findAllByMemberCardInfo(MemberCardInfo memberCardInfo);

    boolean existsByFinancialMemberCardPaymentId(Integer financialMemberCardPaymentId);
}
