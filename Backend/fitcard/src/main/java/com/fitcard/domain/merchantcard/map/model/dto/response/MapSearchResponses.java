package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "검색 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapSearchResponses {

    @Schema(description = "검색 목록")
    private List<MapSearchResponse> mapSearchResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static MapSearchResponses from(List<MapSearchResponse> mapSearchResponses) {
        return new MapSearchResponses(mapSearchResponses, mapSearchResponses.size());
    }
}
