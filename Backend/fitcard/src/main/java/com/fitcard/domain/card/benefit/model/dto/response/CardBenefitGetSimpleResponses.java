package com.fitcard.domain.card.benefit.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Schema(description = "카드 혜택 간단한 정보 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CardBenefitGetSimpleResponses {

    private List<BenefitSimple> benefitSimples;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static CardBenefitGetSimpleResponses from(List<BenefitSimple> benefitSimples) {
        return new CardBenefitGetSimpleResponses(benefitSimples, benefitSimples.size());
    }
}
