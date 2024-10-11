package com.fitcard.domain.card.benefit.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class CardBenefitNotFoundException extends BusinessException {
    public CardBenefitNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardBenefitNotFoundException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
