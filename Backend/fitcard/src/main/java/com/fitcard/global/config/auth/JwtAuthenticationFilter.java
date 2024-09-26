package com.fitcard.global.config.auth;

import com.fitcard.global.config.auth.exception.TokenInvalidException;
import com.fitcard.global.error.ErrorCode;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Authorization 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 토큰 검증 및 인증 설정
        if(token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if(!jwtTokenProvider.validateToken(token))
            throw new TokenInvalidException(ErrorCode.INVALID_TOKEN, "Access token이 만료됐습니다.");

        Claims claims = jwtTokenProvider.getClaims(token);
        Authentication authentication = jwtTokenProvider.getAuthentication(claims);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 토큰 값 반환
        }
        return null;
    }
}
