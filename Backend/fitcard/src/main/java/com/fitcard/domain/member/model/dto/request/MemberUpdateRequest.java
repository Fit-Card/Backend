package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 정보 수정 요청 DTO", description = "사용자 정보를 수정할 때 필요한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberUpdateRequest {

    @Schema(description = "새 비밀번호", example = "newpassword123")
    private String newPassword;

    @Schema(description = "새 전화번호", example = "010-9876-5432")
    private String newPhoneNumber;
}
