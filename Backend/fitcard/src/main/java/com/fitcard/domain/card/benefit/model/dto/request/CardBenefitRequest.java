package com.fitcard.domain.card.benefit.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "카드 혜택 조회 request dto", description = "카드의 혜택 정보를 조회합니다.")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CardBenefitRequest {

    @Schema(description = "카드 ID", example = "3")
    private int cardId;

    @Schema(description = "카드 실적 레벨", example = "1")
    private int level;

}
