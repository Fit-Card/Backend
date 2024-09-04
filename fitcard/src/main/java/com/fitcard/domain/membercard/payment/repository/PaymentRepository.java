package com.fitcard.domain.membercard.payment.repository;

import com.fitcard.domain.membercard.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
