package com.fitcard.domain.membercard.membercardinfo.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberCardCreateMemberCardsException extends BusinessException {
    public MemberCardCreateMemberCardsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberCardCreateMemberCardsException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
