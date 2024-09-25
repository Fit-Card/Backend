package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "분점 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchGetResponses {

    @Schema(description = "분점 목록")
    private List<BranchGetResponse> branchResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static BranchGetResponses from(List<BranchGetResponse> branchResponses) {
        return new BranchGetResponses(branchResponses, branchResponses.size());
    }
}
