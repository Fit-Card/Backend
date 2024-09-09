package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Schema(name = "지도 검색 응답 dto", description = "지도 검색 결과 응답 DTO")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapSearchResponse {

    @Schema(description = "검색 결과 이름", example = "바나프레소 역삼점")
    private String placeName;

    @Schema(description = "검색 결과 주소", example = "서울특별시 강남구 논현로 94길 13")
    private String address;

    @Schema(description = "거리", example = "0.8")
    private Double distance;
}
