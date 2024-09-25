package com.financial.domain.bank.usercard.exception;

import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;

public class BankUserCardGetAllUserCardsException extends BusinessException {
    public BankUserCardGetAllUserCardsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BankUserCardGetAllUserCardsException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
