package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 사용자 카드 조회 응답 DTO", description = "가맹점 사용자 카드 조회 응답 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchCalculateBenefitResponse {
    @Schema(description = "혜택 설명", example = "10%할인")
    private String benefitDescription;

    @Schema(description = "혜택 금액", example = "9500")
    private Double benefitResult;

    public static BranchCalculateBenefitResponse of(String benefitDescription, double benefitResult){
        return new BranchCalculateBenefitResponse(benefitDescription,benefitResult);
    }
}
