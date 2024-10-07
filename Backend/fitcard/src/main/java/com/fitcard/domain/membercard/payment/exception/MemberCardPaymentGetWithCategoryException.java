package com.fitcard.domain.membercard.payment.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardPaymentGetWithCategoryException extends BusinessException {
    public MemberCardPaymentGetWithCategoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardPaymentGetWithCategoryException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
