package com.fitcard.domain.member.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "사용자 정보 수정 응답 DTO", description = "사용자 정보 수정 결과를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateResponse {

    @Schema(description = "수정 결과 메시지", example = "회원 정보 수정에 성공했습니다.")
    private String message;
}
