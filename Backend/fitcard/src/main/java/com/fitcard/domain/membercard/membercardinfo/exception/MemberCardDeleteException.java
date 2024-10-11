package com.fitcard.domain.membercard.membercardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardDeleteException extends BusinessException {
    public MemberCardDeleteException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardDeleteException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
