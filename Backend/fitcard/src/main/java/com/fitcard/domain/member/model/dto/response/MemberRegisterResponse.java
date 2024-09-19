package com.fitcard.domain.member.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@Schema(name = "사용자 회원가입 응답 DTO", description = "사용자 회원가입 결과 응답")
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRegisterResponse {

    @Schema(description = "사용자 아이디", example = "user123")
    private String loginId;

    @Schema(description = "회원가입 결과 메시지", example = "회원가입 성공")
    private String message;

    public static MemberRegisterResponse of(String loginId, String message) {
        return new MemberRegisterResponse(loginId, message);
    }

}
