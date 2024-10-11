package com.fitcard.domain.membercard.payment.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardPaymentGetStatusException extends BusinessException {
    public MemberCardPaymentGetStatusException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardPaymentGetStatusException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
