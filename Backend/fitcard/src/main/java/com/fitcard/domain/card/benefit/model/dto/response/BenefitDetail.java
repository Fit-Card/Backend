package com.fitcard.domain.card.benefit.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(name = "BenefitDetail", description = "혜택 상세 정보")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BenefitDetail {

    @Schema(description = "가맹점 이름", example = "버거킹")
    private String merchantName;

    @Schema(description = "할인 혜택", example = "10% 할인")
    private String discount;

    public static BenefitDetail of(String merchantName, String discount) {
        return new BenefitDetail(merchantName, discount);
    }
}
