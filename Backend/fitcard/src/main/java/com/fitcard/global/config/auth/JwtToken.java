package com.fitcard.global.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtToken {
    private final String accessToken;
    private final String refreshToken;
}