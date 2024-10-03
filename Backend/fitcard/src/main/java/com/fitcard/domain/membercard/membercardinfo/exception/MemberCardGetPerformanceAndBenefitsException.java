package com.fitcard.domain.membercard.membercardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardGetPerformanceAndBenefitsException extends BusinessException {
    public MemberCardGetPerformanceAndBenefitsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardGetPerformanceAndBenefitsException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
