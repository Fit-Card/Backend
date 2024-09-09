package com.fitcard.domain.member.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@Schema(name = "아이디 중복 확인 응답 DTO", description = "아이디 중복 확인 결과 응답")
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCheckIdResponse {

    @Schema(description = "아이디 중복 여부", example = "false")
    private boolean isDuplicated;
}
