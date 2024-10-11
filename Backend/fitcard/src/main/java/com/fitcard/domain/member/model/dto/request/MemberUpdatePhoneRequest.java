package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 전화번호 수정 요청 DTO", description = "사용자 전화번호를 수정할 때 필요한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberUpdatePhoneRequest {

    @NotBlank
    @Schema(description = "새 전화번호", example = "010-1234-5678")
    private String newPhoneNumber;
}
