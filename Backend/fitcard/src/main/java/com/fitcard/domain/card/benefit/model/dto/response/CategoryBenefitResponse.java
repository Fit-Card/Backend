package com.fitcard.domain.card.benefit.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(name = "CategoryBenefitResponse", description = "카테고리별 혜택 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryBenefitResponse {

    @Schema(description = "카테고리 이름", example = "음식점")
    private String name;

    @Schema(description = "카테고리에 해당하는 혜택 목록")
    private List<BenefitDetail> benefits;

    public static CategoryBenefitResponse of(String name, List<BenefitDetail> benefits) {
        return new CategoryBenefitResponse(name, benefits);
    }
}