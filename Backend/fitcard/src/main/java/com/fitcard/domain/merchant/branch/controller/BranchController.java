package com.fitcard.domain.merchant.branch.controller;

import com.fitcard.domain.merchant.branch.model.dto.request.BranchSearchRequest;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchGetResponse;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchGetResponses;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchSearchResponse;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchSearchResponses;
import com.fitcard.domain.merchant.branch.service.BranchService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "가맹점 관련 API")
@RestController
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {
    private final BranchService branchService;

    @Operation(summary = "가맹점 분점 전체 조회 API", description = "모든 가맹점 분점 리스트를 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 분점 리스트 조회에 성공했습니다.")
    @PostMapping("/get/all")
    public Response<BranchGetResponses> getAllBranches() {
        List<BranchGetResponse> branches = branchService.getBranchesAll();
        return Response.SUCCESS(BranchGetResponses.from(branches), "가맹점 분점 리스트 조회에 성공했습니다.");
    }

    @Operation(summary = "가맹점 분점 상세 조회 API", description = "가맹점 분점을 상세 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 분점 상세 조회에 성공했습니다.")
    @PostMapping("/get/one/{merchantBranchId}")
    public Response<BranchGetResponse> getBranch(@PathVariable final Long merchantBranchId){
        BranchGetResponse branch = branchService.getBranchById(merchantBranchId);
        return Response.SUCCESS(branch, "가맹점 분점 상세 조회에 성공했습니다.");
    }

    @Operation(summary = "가맹점 분점 키워드 검색 조회 API", description = "가맹점 분점 리스트를 검색 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 분점 키워드 검색 조회에 성공했습니다.")
    @PostMapping("/search")
    public Response<BranchGetResponses> getBranchesByMerchantName(@RequestBody final BranchSearchRequest request){
        List<BranchGetResponse> branches = branchService.getBranchesByMerchantKeyword(request);
        return Response.SUCCESS(BranchGetResponses.from(branches), "가맹점 분점 리스트 검색 조회에 성공했습니다.");
    }

    @Operation(summary = "가맹점 분점 키워드 검색 조회 API(페이지네이션)", description = "가맹점 분점 리스트를 검색 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 분점 키워드 검색 조회에 성공했습니다.(페이지네이션)")
    @PostMapping("/search-page")
    public Response<BranchSearchResponses> getBranchesByMerchantName(@RequestBody final BranchSearchRequest request, @RequestParam int pageNo){
        List<BranchSearchResponse> branches = branchService.getBranchesByMerchantKeywordPagination(request, pageNo);
        return Response.SUCCESS(BranchSearchResponses.from(branches), "가맹점 분점 리스트 검색 조회에 성공했습니다.(페이지네이션)");
    }
}
