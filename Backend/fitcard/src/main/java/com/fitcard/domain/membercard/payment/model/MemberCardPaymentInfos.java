package com.fitcard.domain.membercard.payment.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPaymentInfos {

    private List<Payment> payments;

    private int totalAmount;

    public static MemberCardPaymentInfos of(List<Payment> payments, int totalAmount) {
        return new MemberCardPaymentInfos(payments, totalAmount);
    }
}
