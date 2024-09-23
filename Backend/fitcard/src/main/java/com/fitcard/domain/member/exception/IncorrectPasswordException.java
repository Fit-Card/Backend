package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class IncorrectPasswordException extends BusinessException {

    public IncorrectPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IncorrectPasswordException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
