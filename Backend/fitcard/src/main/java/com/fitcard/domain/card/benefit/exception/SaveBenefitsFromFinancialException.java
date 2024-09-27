package com.fitcard.domain.card.benefit.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class SaveBenefitsFromFinancialException extends BusinessException {
    public SaveBenefitsFromFinancialException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SaveBenefitsFromFinancialException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
