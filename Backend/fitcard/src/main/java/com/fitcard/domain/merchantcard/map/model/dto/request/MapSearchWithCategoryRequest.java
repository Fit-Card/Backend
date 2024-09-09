package com.fitcard.domain.merchantcard.map.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "카테고리별 지도 검색 요청 DTO", description = "카테고리별로 지도 검색을 위한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MapSearchWithCategoryRequest {
    @Schema(description = "카테고리", example = "편의점")
    private String category;

    @Schema(description = "사용자 위도", example = "37.5665")
    private Double latitude;

    @Schema(description = "사용자 경도", example = "126.9780")
    private Double longitude;
}
