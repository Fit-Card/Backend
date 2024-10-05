package com.fitcard.domain.membercard.payment.repository;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

//    List<Payment> findAllByMemberCard(MemberCardInfo memberCardInfo);

    boolean existsByFinancialMemberCardPaymentId(Integer financialMemberCardPaymentId);

    List<Payment> findAllByMemberCardAndPaymentDateBetween(MemberCardInfo memberCardInfo, LocalDateTime startOfMonth, LocalDateTime now);


}
