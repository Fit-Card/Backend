package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class DuplicatedMemberException extends BusinessException {

    public DuplicatedMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DuplicatedMemberException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
