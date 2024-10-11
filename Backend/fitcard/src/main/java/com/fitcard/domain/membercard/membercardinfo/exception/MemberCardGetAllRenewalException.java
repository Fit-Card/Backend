package com.fitcard.domain.membercard.membercardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardGetAllRenewalException extends BusinessException {
    public MemberCardGetAllRenewalException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardGetAllRenewalException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
