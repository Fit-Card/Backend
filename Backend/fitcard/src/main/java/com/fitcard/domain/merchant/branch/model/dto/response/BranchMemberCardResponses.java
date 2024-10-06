package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "가맹점별 사용자 카드 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchMemberCardResponses {

    private List<BranchMemberCardResponse> branchMemberCardResponseList;

    public static BranchMemberCardResponses from (List<BranchMemberCardResponse> branchMemberCardResponseList){
        return new BranchMemberCardResponses(branchMemberCardResponseList);
    }
}
