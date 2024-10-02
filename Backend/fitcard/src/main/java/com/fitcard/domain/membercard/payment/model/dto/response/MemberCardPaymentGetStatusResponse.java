package com.fitcard.domain.membercard.payment.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 카드 실적 현황 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPaymentGetStatusResponse {

    @Schema(description = "이번달 지출 금액", example = "450000")
    private int totalPayment;

    @Schema(description = "실적 구간 start 금액", example = "150000")
    private int performanceStart;

    @Schema(description = "실적 구간 end 금액", example = "500000")
    private int performanceEnd;

    @Schema(description = "실적 구간 레벨", example = "2")
    private int performanceLevel;

    public static MemberCardPaymentGetStatusResponse of(int totalPayment, int performanceStart, int performanceEnd, int performanceLevel) {
        return new MemberCardPaymentGetStatusResponse(totalPayment, performanceStart, performanceEnd, performanceLevel);
    }

}
