package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 분점 조회 응답 DTO", description = "가맹점 분점 정보를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchGetAllResponse {
    @Schema(description = "분점 이름", example = "신한카드 본점")
    private String branchName;

    @Schema(description = "분점 주소", example = "서울특별시 중구 을지로 123")
    private String branchAddress;

    @Schema(description = "위도", example = "37.5665")
    private Double latitude;

    @Schema(description = "경도", example = "126.9780")
    private Double longitude;

    @Schema(description = "카카오맵 링크", example = "https://map.kakao.com/link/map/37.5665,126.9780")
    private String kakaoUrl;

    @Schema(description = "예외 타입", example = "공항")
    private String exceptionType;

}
