package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "카테고리별 검색 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapSearchWithCategoryResponses {

    @Schema(description = "카테코리별 검색 목록")
    private List<MapSearchWithCategoryResponse> mapSearchWithCategoryResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public MapSearchWithCategoryResponses from(List<MapSearchWithCategoryResponse> mapSearchWithCategoryResponses) {
        return new MapSearchWithCategoryResponses(mapSearchWithCategoryResponses, mapSearchWithCategoryResponses.size());
    }
}
