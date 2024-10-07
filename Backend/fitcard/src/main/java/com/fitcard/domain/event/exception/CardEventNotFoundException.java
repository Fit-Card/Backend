package com.fitcard.domain.event.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class CardEventNotFoundException extends BusinessException {
    public CardEventNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardEventNotFoundException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
