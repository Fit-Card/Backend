package com.fitcard.domain.card.performance.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class CardPerformanceNotFoundException extends BusinessException {
    public CardPerformanceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardPerformanceNotFoundException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
