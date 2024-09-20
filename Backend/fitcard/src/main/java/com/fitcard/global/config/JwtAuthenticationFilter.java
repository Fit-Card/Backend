package com.fitcard.global.config;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtAuthenticationFilter implements ServerAuthenticationConverter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = resolveToken(exchange);
//        log.info("토큰: {}", token);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.getClaims(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(claims);
//            log.info("Authentication: {}", authentication);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    token,  // 토큰을 Credentials로 설정
                    authentication.getAuthorities()
            );

            return Mono.<Authentication>just(auth)
                    .doOnNext(ReactiveSecurityContextHolder::withAuthentication);
        }
        return Mono.empty();
    }

    // Authorization 헤더에서 토큰 추출
    private String resolveToken(ServerWebExchange exchange) {
        String bearerToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
