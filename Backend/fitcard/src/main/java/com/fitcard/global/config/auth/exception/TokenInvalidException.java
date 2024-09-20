package com.fitcard.global.config.auth.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class TokenInvalidException extends BusinessException {

    public TokenInvalidException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenInvalidException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
