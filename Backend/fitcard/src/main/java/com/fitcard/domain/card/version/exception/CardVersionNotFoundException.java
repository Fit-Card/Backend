package com.fitcard.domain.card.version.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class CardVersionNotFoundException extends BusinessException {
    public CardVersionNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardVersionNotFoundException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
