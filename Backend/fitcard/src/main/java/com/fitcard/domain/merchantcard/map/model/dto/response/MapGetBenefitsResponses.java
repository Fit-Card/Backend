package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "가맹점 카드 혜택 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapGetBenefitsResponses {

    @Schema(description = "가맹점 카드 혜택 목록")
    private List<MapGetBenefitsResponse> mapGetBenefitsResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public MapGetBenefitsResponses from(List<MapGetBenefitsResponse> mapGetBenefitsResponses) {
        return new MapGetBenefitsResponses(mapGetBenefitsResponses, mapGetBenefitsResponses.size());
    }
}
