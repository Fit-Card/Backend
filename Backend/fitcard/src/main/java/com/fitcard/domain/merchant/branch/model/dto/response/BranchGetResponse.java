package com.fitcard.domain.merchant.branch.model.dto.response;

import com.fitcard.domain.merchant.branch.model.Branch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 분점 조회 응답 DTO", description = "가맹점 분점 정보를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchGetResponse {
    @Schema(description = "분점 Id", example = "1")
    private Long merchantBranchId;

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

    public static BranchGetResponse from(Branch branch){
        return new BranchGetResponse(branch.getMerchantBranchId(), branch.getBranchName(), branch.getAddress(), branch.getY(), branch.getX(), branch.getKakaoUrl());
    }
}
