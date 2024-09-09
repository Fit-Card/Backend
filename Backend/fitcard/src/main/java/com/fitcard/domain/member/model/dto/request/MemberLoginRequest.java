package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 로그인 요청 DTO", description = "사용자 로그인에 필요한 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberLoginRequest {
    @Schema(description = "사용자 아이디", example = "user123")
    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @Schema(description = "사용자 비밀번호", example = "password123")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
