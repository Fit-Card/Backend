package com.fitcard.global.config.auth.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class TokenExpiredException extends BusinessException {

    public TokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenExpiredException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
