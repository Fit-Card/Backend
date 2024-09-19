package com.financial.domain.bank.cardbenefit.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "카드사 카드 혜택 추가 request dto", description = "카드사 카드 혜택 추가")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BankCardBenefitAddRequest {

    @Schema(description = "카드 실적 ID", example = "123")
    @NotNull
    private Long cardPerformanceId;

    @Schema(description = "카드 혜택 타입 (DISCOUNT, POINT 등)", example = "DISCOUNT")
    @NotNull
    private String benefitType;

    @Schema(description = "카드 할인 금액 한도", example = "10000")
    @NotNull
    private int amountLimit;

    @Schema(description = "카드 할인 횟수 한도", example = "일 1회")
    @NotNull
    private String countLimit;

    @Schema(description = "최소 결제 금액", example = "50000")
    @NotNull
    private int minPayment;

    @Schema(description = "혜택 수치", example = "1000")
    @NotNull
    private double benefitValue;

    @Schema(description = "OO 당 혜택 금액", example = "10000")
    @NotNull
    private int benefitPer;

    @Schema(description = "가맹점 카테고리", example = "편의점")
    @NotNull
    private String merchantCategory;

    @Schema(description = "가맹점 ID", example = "15000")
    @NotNull
    private int merchantId;

    @Schema(description = "할인 예외 항목", example = "공항, 면세점")
    private String exceptionTypeList;
}
