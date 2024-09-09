package com.fitcard.domain.card.benefit.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "카드 혜택 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardBenefitGetResponse {

    @Schema(description = "카드 혜택 타입 (DISCOUNT, POINT, ...)", example = "DISCOUNT")
    private String benefitType;

    @Schema(description = "카드 할인 금액 한도", example = "15000")
    private int amountLimit;

    @Schema(description = "카드 할인 횟수 한도", example = "3")
    private String countLimit;

    @Schema(description = "최소 결제 금액", example = "50000")
    private int minPayment;

    @Schema(description = "할인되는 금액, %, 포인트 값", example = "10000")
    private double benefitValue;

    @Schema(description = "만 원 당 500원에서 만 원", example = "10000")
    private int benefitPer;

    @Schema(description = "가맹점 카테고리", example = "Food")
    private String merchantCategory;

    @Schema(description = "가맹점 ID ", example = "15000")
    private int merchantId;

    @Schema(description = "가맹점 이름, All이면 카테고리 전체, 아니면 특정 가맹점 이름", example = "파리바게트")
    private String merchantName;

    @Schema(description = "할인 예외 리스트", example = "[공항, 면세점]")
    private String exceptionTypes;
}
