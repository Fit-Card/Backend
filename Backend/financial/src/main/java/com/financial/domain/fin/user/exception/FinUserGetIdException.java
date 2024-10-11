package com.financial.domain.fin.user.exception;

import com.financial.global.error.BusinessException;
import com.financial.global.error.ErrorCode;

public class FinUserGetIdException extends BusinessException {
    public FinUserGetIdException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FinUserGetIdException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
