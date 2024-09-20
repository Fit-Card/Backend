package com.fitcard.global.config.auth;

import com.fitcard.global.config.auth.exception.UnauthorizedException;
import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    // 로그인 ID를 가져오는 메서드
    public static String getLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_ACCESS, "인증되지 않은 사용자입니다.");
        }
        return authentication.getName();  // 로그인 ID 반환
    }
}
