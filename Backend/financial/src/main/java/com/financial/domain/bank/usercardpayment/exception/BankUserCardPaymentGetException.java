package com.financial.domain.bank.usercardpayment.exception;

import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;

public class BankUserCardPaymentGetException extends BusinessException {
    public BankUserCardPaymentGetException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BankUserCardPaymentGetException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
