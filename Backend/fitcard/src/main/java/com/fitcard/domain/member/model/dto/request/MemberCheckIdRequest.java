package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "아이디 중복 확인 요청 DTO", description = "아이디 중복 확인에 필요한 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCheckIdRequest {

    @Schema(description = "사용자 아이디", example = "user123")
    @NotBlank(message = "아이디를 입력하세요.")
    private String loginId;
}
