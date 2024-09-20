package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberUpdateFailedException extends BusinessException {

    public MemberUpdateFailedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberUpdateFailedException(ErrorCode errorCode ,String message) {
        super(errorCode, message);
    }
}
