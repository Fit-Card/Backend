package com.fitcard.domain.card.cardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class CardInfoNotFoundException extends BusinessException {
    public CardInfoNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardInfoNotFoundException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
