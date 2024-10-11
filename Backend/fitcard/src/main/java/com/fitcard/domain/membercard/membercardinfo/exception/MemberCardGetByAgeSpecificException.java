package com.fitcard.domain.membercard.membercardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardGetByAgeSpecificException extends BusinessException {
    public MemberCardGetByAgeSpecificException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardGetByAgeSpecificException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
