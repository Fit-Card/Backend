package com.fitcard.domain.merchantcard.map.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "지도 검색 요청 DTO", description = "지도 검색을 위한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MapSearchRequest {

    @Schema(description = "검색 키워드", example = "바나프레소")
    private String keyword;
}
