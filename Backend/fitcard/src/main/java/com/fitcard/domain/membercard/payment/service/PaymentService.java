package com.fitcard.domain.membercard.payment.service;

import com.fitcard.domain.membercard.payment.model.dto.request.MemberCardPaymentGetStatusRequest;
import com.fitcard.domain.membercard.payment.model.dto.response.MemberCardPaymentGetStatusResponse;
import com.fitcard.domain.membercard.payment.model.dto.response.MemberCardPaymentGetWithCategoryResponse;

public interface PaymentService {

    MemberCardPaymentGetStatusResponse getMemberCardPaymentStatus(MemberCardPaymentGetStatusRequest request);

    MemberCardPaymentGetWithCategoryResponse getMemberCardPaymentWithCategory(int memberId);

}
