package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "분점 목록")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchGetResponses {

    private List<BranchGetResponse> branchResponses;

    public static BranchGetResponses from(List<BranchGetResponse> branchResponses) {
        return new BranchGetResponses(branchResponses);
    }
}
