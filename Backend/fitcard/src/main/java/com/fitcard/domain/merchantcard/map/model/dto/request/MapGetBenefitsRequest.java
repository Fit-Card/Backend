package com.fitcard.domain.merchantcard.map.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹점 내 카드 혜택 조회 요청 DTO", description = "가맹점에서 내 카드 혜택을 조회할 때 사용하는 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MapGetBenefitsRequest {
    @Schema(description = "가맹점 ID", example = "1")
    private Long merchantId;
}
