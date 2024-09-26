package com.fitcard.domain.card.performance.model.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Financial 카드 실적 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FinancialCardPerformanceResponses {

    @Schema(description = "실적 조회 목록")
    private List<FinancialCardPerformanceResponse> bankCardPerformanceGetResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static FinancialCardPerformanceResponses from(List<FinancialCardPerformanceResponse> financialCardPerformanceResponses) {
        return new FinancialCardPerformanceResponses(financialCardPerformanceResponses, financialCardPerformanceResponses.size());
    }

}
