package com.fitcard.domain.merchantcard.map.controller;

import com.fitcard.domain.merchantcard.map.model.dto.request.MapSearchRequest;
import com.fitcard.domain.merchantcard.map.model.dto.request.MapSearchWithCategoryRequest;
import com.fitcard.domain.merchantcard.map.model.dto.request.MapGetBenefitsRequest;
import com.fitcard.domain.merchantcard.map.model.dto.request.MapCalculateBenefitsRequest;
import com.fitcard.domain.merchantcard.map.model.dto.response.*;
import com.fitcard.domain.merchantcard.map.service.MapService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "지도 관련 API")
@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @Operation(summary = "지도 검색 API", description = "키워드를 통해 카카오 맵에서 위치 정보를 검색합니다.")
    @SwaggerApiSuccess(description = "카카오 맵 검색에 성공했습니다.")
    @PostMapping("/search")
    public Response<MapSearchResponses> searchMap(@RequestBody MapSearchRequest request) {
        return Response.SUCCESS(null, "카카오 맵 검색에 성공했습니다.");
    }

    @Operation(summary = "카테고리별 사용자 카드 혜택 조회 API", description = "사용자의 위치와 카테고리를 기반으로 카드 혜택을 받을 수 있는 장소를 조회합니다.")
    @SwaggerApiSuccess(description = "카테고리별 혜택 조회에 성공했습니다.")
    @PostMapping("/search/category")
    public Response<MapSearchWithCategoryResponses> searchWithCategory(@RequestBody MapSearchWithCategoryRequest request) {
        return Response.SUCCESS(null, "카테고리별 혜택 조회에 성공했습니다.");
    }

    @Operation(summary = "내 카드로 혜택을 받을 수 있는 가맹점 조회 API", description = "특정 가맹점에서 사용자의 카드로 혜택을 받을 수 있는 정보 조회")
    @SwaggerApiSuccess(description = "내 카드 가맹점 혜택 조회에 성공했습니다.")
    @PostMapping("/get/benefits")
    public Response<MapGetBenefitsResponses> getBenefits(@RequestBody MapGetBenefitsRequest request) {
        return Response.SUCCESS(null, "가맹점 혜택 조회에 성공했습니다.");
    }

    @Operation(summary = "사용자 혜택 계산 API", description = "유저 카드 정보와 금액을 기반으로 카드 혜택을 계산합니다.")
    @SwaggerApiSuccess(description = "혜택 계산에 성공했습니다.")
    @PostMapping("/calculate/benefits")
    public Response<MapCalculateBenefitsResponses> calculateBenefits(@RequestBody MapCalculateBenefitsRequest request) {
        return Response.SUCCESS(null, "혜택 계산에 성공했습니다.");
    }
}
