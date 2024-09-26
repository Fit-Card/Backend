package com.fitcard.global.config.auth;

import com.fitcard.global.config.auth.exception.TokenExpiredException;
import com.fitcard.global.config.auth.exception.TokenInvalidException;
import com.fitcard.global.error.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;

    // 생성자를 통해 secret, 만료 시간을 주입받음
    public JwtTokenProvider(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.expiration-time}") long accessTokenExpirationTime,
            @Value("${spring.jwt.refresh-expiration-time}") long refreshTokenExpirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    // Access Token 생성
    public String createAccessToken(String username, String memberId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("memberId", memberId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Refresh Token 생성
    public String createRefreshToken() {
        Claims claims = Jwts.claims();
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenExpirationTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidException(ErrorCode.INVALID_TOKEN, "유효하지 않은 토큰입니다.");
        }
    }

    // 토큰에서 사용자 이름(Subject) 추출
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    //토큰에서 사용자 id 추출
    public String getMemberId(String token) {
        log.info("memberId: {}", getClaims(token).get("memberId"));
        return getClaims(token).get("memberId").toString();
//        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("memberId").toString();
    }

    // 클레임에서 Authentication 객체 생성
    public Authentication getAuthentication(Claims claims) {
        String username = claims.getSubject();  // JWT 클레임에서 사용자 이름 추출
        return new UsernamePasswordAuthenticationToken(username, null, null);
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException(ErrorCode.INVALID_TOKEN, "토큰이 만료되었습니다.");
        }
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  // "Bearer " 이후의 토큰 값 반환
        }
        return bearerToken;
    }

}
