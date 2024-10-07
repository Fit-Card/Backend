package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(name = "가맹점 사용자 카드 조회 응답 DTO", description = "가맹점 사용자 카드 조회 응답 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchCalculateBenefitResponses {

    List<BranchCalculateBenefitResponse> branchCalculateBenefitResponses;

    public static BranchCalculateBenefitResponses from(List<BranchCalculateBenefitResponse> branchCalculateBenefitResponses){
        return new BranchCalculateBenefitResponses(branchCalculateBenefitResponses);
    }
}
