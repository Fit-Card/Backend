package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 회원가입 요청 DTO", description = "사용자 회원가입에 필요한 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberRegisterRequest {

    @Schema(description = "사용자 아이디", example = "user123")
    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;

    @Schema(description = "사용자 비밀번호", example = "password123")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @Schema(description = "사용자 이름", example = "홍길동")
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @Schema(description = "전화번호", example = "010-1234-5678")
    @NotBlank(message = "전화번호를 입력하세요.")
    private String phoneNumber;
}
