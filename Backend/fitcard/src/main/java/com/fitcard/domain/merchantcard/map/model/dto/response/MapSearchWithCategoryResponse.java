package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Schema(name = "카테고리별 지도 검색 응답 dto", description = "카테고리별 지도 검색 결과 응답 DTO")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapSearchWithCategoryResponse {
    @Schema(description = "장소 이름", example = "스타벅스 강남역점")
    private String placeName;

    @Schema(description = "장소 주소", example = "서울특별시 강남구 강남대로 123")
    private String address;

    @Schema(description = "위도", example = "37.5665")
    private Double latitude;

    @Schema(description = "경도", example = "126.9780")
    private Double longitude;
}
