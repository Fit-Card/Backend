package com.fitcard.domain.member.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Schema(name = "사용자 정보 응답 DTO", description = "사용자 정보를 조회하여 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberGetResponse {
    @Schema(description = "사용자 아이디", example = "user123")
    private String loginId;

    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;

    @Schema(description = "사용자 전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "사용자 생년월일", example = "1990-01-01")
    private LocalDate birthDate;

    @Schema(description = "마이데이터 인증 여부", example = "true")
    private Boolean isCertifiedMydata;
}
