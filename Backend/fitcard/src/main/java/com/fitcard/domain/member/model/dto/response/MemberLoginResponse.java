package com.fitcard.domain.member.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "사용자 로그인 응답 DTO", description = "사용자 로그인 결과 응답")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginResponse {

    @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String token;

    @Schema(description = "로그인 결과 메시지", example = "로그인 성공")
    private String message;
}
