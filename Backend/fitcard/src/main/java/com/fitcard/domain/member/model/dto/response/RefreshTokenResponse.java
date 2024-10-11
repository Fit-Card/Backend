package com.fitcard.domain.member.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "JWT 토큰 재발급 응답 DTO", description = "JWT 토큰 재발급 결과 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenResponse {

    @Schema(description = "새로운 JWT access 토큰", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String newAccessToken;

    public static RefreshTokenResponse from(String newAccessToken) {
        return new RefreshTokenResponse(newAccessToken);
    }
}
