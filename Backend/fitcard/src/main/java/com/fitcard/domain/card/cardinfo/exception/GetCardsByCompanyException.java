package com.fitcard.domain.card.cardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class GetCardsByCompanyException extends BusinessException {
    public GetCardsByCompanyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public GetCardsByCompanyException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
