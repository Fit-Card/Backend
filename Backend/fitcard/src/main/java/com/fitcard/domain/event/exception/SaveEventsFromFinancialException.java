package com.fitcard.domain.event.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class SaveEventsFromFinancialException extends BusinessException {
    public SaveEventsFromFinancialException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SaveEventsFromFinancialException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
