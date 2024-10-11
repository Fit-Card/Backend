package com.fitcard.domain.membercard.recommend.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardRecommendGetAllException extends BusinessException {
    public MemberCardRecommendGetAllException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardRecommendGetAllException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
