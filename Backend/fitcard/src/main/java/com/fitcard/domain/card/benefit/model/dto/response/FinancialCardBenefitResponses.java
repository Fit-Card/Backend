package com.fitcard.domain.card.benefit.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Financial 카드 혜택 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FinancialCardBenefitResponses {

    @Schema(description = "혜택 조회 목록")
    private List<FinancialCardBenefitResponse> bankCardBenefitGetResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static FinancialCardBenefitResponses from(List<FinancialCardBenefitResponse> financialCardBenefitResponses){
        return new FinancialCardBenefitResponses(financialCardBenefitResponses, financialCardBenefitResponses.size());
    }


}
