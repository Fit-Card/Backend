package com.fitcard.domain.member.exception;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class MemberGetUserSeqNoFromFinancialException extends BusinessException {

    public MemberGetUserSeqNoFromFinancialException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberGetUserSeqNoFromFinancialException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
