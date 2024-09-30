package com.fitcard.domain.card.benefit.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(name = "CardBenefitResponse", description = "단일 카드 혜택 조회 응답 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardBenefitResponse {

    @Schema(description = "카드 이름", example = "신한카드 B.Big(삑)")
    private String cardName;

    @Schema(description = "카드 이미지 URL", example = "https://example.com/card_image.png")
    private String cardImage;

    @Schema(description = "카테고리별 혜택 목록")
    private List<CategoryBenefitResponse> categories;

    public static CardBenefitResponse of(String cardName, String cardImage, List<CategoryBenefitResponse> categories) {
        return new CardBenefitResponse(cardName, cardImage, categories);
    }
}
