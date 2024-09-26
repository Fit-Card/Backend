package com.fitcard.domain.membercard.membercardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardGetAllException extends BusinessException {
    public MemberCardGetAllException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardGetAllException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
