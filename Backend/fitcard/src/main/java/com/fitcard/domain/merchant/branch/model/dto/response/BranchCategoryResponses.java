package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "분점 목록")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchCategoryResponses {

    private List<BranchCategoryResponse> branchResponses;

    private int currentPage;

    private int totalPages;

    public static BranchCategoryResponses of(List<BranchCategoryResponse> branchResponses, Page<Object[]> page) {
        return new BranchCategoryResponses(branchResponses, page.getNumber() + 1, page.getTotalPages());
    }
}
