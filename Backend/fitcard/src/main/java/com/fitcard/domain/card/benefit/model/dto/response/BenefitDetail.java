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

    @Schema(description = "카드 할인 금액 한도", example = "일 1000원")
    private String amountLimit;

    @Schema(description = "카드 할인 횟수 한도", example = "월 1회")
    private String countLimit;

    @Schema(description = "최소 결제 금액", example = "50000")
    private int minPayment;

    @Schema(description = "할인 예외 리스트", example = "[공항, 면세점]")
    private String exceptionTypes;

    public static BenefitDetail of(String merchantName, String discount, String amountLimit, String countLimit, int minPayment, String exceptionTypes) {
        return new BenefitDetail(merchantName, discount, amountLimit, countLimit, minPayment, exceptionTypes);
    }
}
