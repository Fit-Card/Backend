package com.fitcard.domain.merchant.branch.service;

import com.fitcard.domain.merchant.branch.model.dto.request.BranchSearchRequest;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchGetResponse;
import com.fitcard.infra.kakao.model.LocalInfo;

import java.util.List;

public interface BranchService {

    //범위만큼 크롤링해서 가맹점과 분점 저장
    int createBranches(List<LocalInfo> request);
    List<BranchGetResponse> getBranchesAll();
    BranchGetResponse getBranchById(final Long merchantBranchId);
    List<BranchGetResponse> getBranchesByMerchantKeyword(final BranchSearchRequest request);
    List<BranchGetResponse> getBranchesByMerchantKeywordPagination(final BranchSearchRequest request, final int page);
}
