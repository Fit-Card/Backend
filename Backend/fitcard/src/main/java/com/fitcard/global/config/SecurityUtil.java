package com.fitcard.global.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

public class SecurityUtil {

    // 로그인 ID를 가져오는 메서드
    public static Mono<String> getLoginId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    Authentication authentication = context.getAuthentication();
                    if (authentication == null || !authentication.isAuthenticated()) {
                        throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
                    }
                    return authentication.getName();  // 로그인 ID 반환
                });
    }
}
