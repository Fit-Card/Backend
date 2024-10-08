package com.fitcard.domain.event.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Financial 카드 이벤트 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FinancialCardEventResponses {

    @Schema(description = "이벤트 조회 목록")
    private List<FinancialCardEventResponse> bankCardEventGetResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static FinancialCardEventResponses from(List<FinancialCardEventResponse> financialCardEventResponses){
        return new FinancialCardEventResponses(financialCardEventResponses, financialCardEventResponses.size());
    }

}
