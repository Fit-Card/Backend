package com.fitcard.domain.card.performance.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class SavePerformancesFromFinancialException extends BusinessException {
    public SavePerformancesFromFinancialException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SavePerformancesFromFinancialException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
