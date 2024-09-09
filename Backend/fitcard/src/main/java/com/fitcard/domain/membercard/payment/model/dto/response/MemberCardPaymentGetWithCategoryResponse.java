package com.fitcard.domain.membercard.payment.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 카드 카테고리별 결제내역 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPaymentGetWithCategoryResponse {

    @Schema(description = "음식점 지출 금액", example = "150000")
    private String restaurant;

    @Schema(description = "카페 지출 금액", example = "100000")
    private String cafe;

    @Schema(description = "편의점 지출 금액", example = "50000")
    private String convenienceStores;

    @Schema(description = "문화 지출 금액", example = "250000")
    private String culture;

    @Schema(description = "주유소 지출 금액", example = "150000")
    private String gasStations;

    @Schema(description = "기타 지출 금액", example = "100000")
    private String other;

    @Schema(description = "지출 금액 총액", example = "700000")
    private String totalAmount;

}
