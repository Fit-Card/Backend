package com.fitcard.global.firebase;

import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;

public class FirebaseException extends BusinessException {
    public FirebaseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FirebaseException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
