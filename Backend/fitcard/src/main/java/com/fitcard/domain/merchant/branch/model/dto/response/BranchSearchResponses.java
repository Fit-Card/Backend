package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "분점 목록")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchSearchResponses {

    private List<BranchSearchResponse> branchResponses;

    public static BranchSearchResponses from(List<BranchSearchResponse> branchResponses) {
        return new BranchSearchResponses(branchResponses);
    }
}
