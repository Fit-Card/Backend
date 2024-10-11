package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class InvalidRefreshTokenException extends BusinessException {

    public InvalidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidRefreshTokenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
