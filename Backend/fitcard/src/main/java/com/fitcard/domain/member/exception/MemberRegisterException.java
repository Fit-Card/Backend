package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberRegisterException extends BusinessException {
    public MemberRegisterException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberRegisterException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
