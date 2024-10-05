package com.fitcard.domain.card.benefit.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class CardBenefitGetSimpleBenefitException extends BusinessException {
    public CardBenefitGetSimpleBenefitException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CardBenefitGetSimpleBenefitException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
