package com.financial.domain.bank.cardperformance.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "카드사 카드 실적 추가 request dto", description = "카드사 카드 실적 추가")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BankCardPerformanceAddRequest {

    @Schema(description = "카드 ID", example = "123")
    @NotNull
    private Long cardId;

    @Schema(description = "카드 실적 레벨", example = "1")
    @NotNull
    private int level;

    @Schema(description = "카드 실적 금액", example = "500000")
    @NotNull
    private int amount;
    
    @Schema(description = "카드 할인 한도", example = "5000")
    @NotNull
    private int benefitLimit;
}
