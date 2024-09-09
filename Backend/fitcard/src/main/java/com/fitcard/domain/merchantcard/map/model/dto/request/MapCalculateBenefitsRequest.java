package com.fitcard.domain.merchantcard.map.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "혜택 계산 요청 DTO", description = "결제 금액을 기반으로 혜택 계산 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MapCalculateBenefitsRequest {

    @Schema(description = "결제 금액", example = "50000")
    private Integer amount;
}
