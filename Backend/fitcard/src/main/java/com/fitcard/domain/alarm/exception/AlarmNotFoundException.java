package com.fitcard.domain.alarm.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class AlarmNotFoundException extends BusinessException {
    public AlarmNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlarmNotFoundException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
