package com.financial.domain.bank.usercard.exception;

import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;

public class BankUserCardGetUserCardException extends BusinessException {
    public BankUserCardGetUserCardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BankUserCardGetUserCardException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
