package com.fitcard.domain.card.cardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class SaveCardsFromFinancialException extends BusinessException {
    public SaveCardsFromFinancialException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SaveCardsFromFinancialException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
