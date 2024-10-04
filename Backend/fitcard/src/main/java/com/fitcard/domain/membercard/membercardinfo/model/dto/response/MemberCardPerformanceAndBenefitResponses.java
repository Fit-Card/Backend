package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 카드 실적 현황과 혜택 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPerformanceAndBenefitResponses {

    private List<MemberCardPerformanceAndBenefitResponse> memberCardPerformanceAndBenefitResponse;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static MemberCardPerformanceAndBenefitResponses from(List<MemberCardPerformanceAndBenefitResponse> memberCardPerformanceAndBenefitRespons) {
        return new MemberCardPerformanceAndBenefitResponses(memberCardPerformanceAndBenefitRespons, memberCardPerformanceAndBenefitRespons.size());
    }
}
