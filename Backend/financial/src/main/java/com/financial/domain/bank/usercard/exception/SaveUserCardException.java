package com.financial.domain.bank.usercard.exception;

import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;

public class SaveUserCardException extends BusinessException {
    public SaveUserCardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SaveUserCardException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
