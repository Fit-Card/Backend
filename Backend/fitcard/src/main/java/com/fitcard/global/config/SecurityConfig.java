package com.fitcard.global.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)  // CSRF 비활성화
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)  // JWT 인증 필터 추가
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/auth/login", "/auth/register", "/auth/checkid", "/kakao/**").permitAll() // 로그인, 회원가입 허용
                        .pathMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()  // Swagger 경로 인증 없이 허용
                        .anyExchange().authenticated()  // 나머지는 인증 필요
                );
        return http.build();
    }

    // PasswordEncoder를 빈으로 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder 사용
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter() {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(jwtReactiveAuthenticationManager());
        authenticationWebFilter.setServerAuthenticationConverter(jwtAuthenticationFilter);  // JwtAuthenticationFilter 사용
        return authenticationWebFilter;
    }

    // JWT 토큰을 검증하는 ReactiveAuthenticationManager
    @Bean
    public ReactiveAuthenticationManager jwtReactiveAuthenticationManager() {
        return authentication -> {
//            log.info("ReactiveAuthenticationManager - Credentials: {}", authentication.getCredentials());
            String token = (String) authentication.getCredentials();
//            log.info("ReactiveAuthenticationManager - Token: {}", token);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Claims claims = jwtTokenProvider.getClaims(token);
                Authentication auth = jwtTokenProvider.getAuthentication(claims);
                return Mono.just(auth);  // 유효한 토큰이면 인증 성공
            } else {
                return Mono.error(new RuntimeException("Invalid token"));  // 유효하지 않은 토큰일 경우 인증 실패 처리
            }
        };
    }
}
