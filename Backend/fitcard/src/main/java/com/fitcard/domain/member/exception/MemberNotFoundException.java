package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberNotFoundException extends BusinessException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
