package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Schema(name = "카드별 혜택 계산 응답 DTO", description = "가맹점에서 카드별 혜택 계산 결과 응답")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapCalculateBenefitsResponse {

    @Schema(description = "카드 이름", example = "신한카드 처음")
    private String cardName;

    @Schema(description = "혜택 양", example = "5000")
    private Double amount;
}
