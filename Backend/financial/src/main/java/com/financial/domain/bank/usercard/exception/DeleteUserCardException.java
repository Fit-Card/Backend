package com.financial.domain.bank.usercard.exception;

import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;

public class DeleteUserCardException extends BusinessException {
    public DeleteUserCardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DeleteUserCardException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
