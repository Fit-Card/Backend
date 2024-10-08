package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class SmsCertificationNumberMismatchException extends BusinessException {
    public SmsCertificationNumberMismatchException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SmsCertificationNumberMismatchException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
