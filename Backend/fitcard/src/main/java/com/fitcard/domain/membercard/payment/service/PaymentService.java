package com.fitcard.domain.membercard.payment.service;

import com.fitcard.domain.membercard.payment.model.dto.request.MemberCardPaymentGetStatusRequest;
import com.fitcard.domain.membercard.payment.model.dto.response.MemberCardPaymentGetStatusResponse;

public interface PaymentService {

    MemberCardPaymentGetStatusResponse getMemberCardPaymentStatus(MemberCardPaymentGetStatusRequest request);
}
