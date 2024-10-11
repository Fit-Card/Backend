package com.fitcard.domain.card.performance.controller;

import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.card.performance.service.CardPerformanceService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카드 실적 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards/performance")
@RequiredArgsConstructor
public class CardPerformanceController {

    private final CardPerformanceService cardPerformanceService;

    @PostMapping("/import")
    public Response<Integer> createCardPerformancesFromFinancial(){
        int savedPerformanceCount = cardPerformanceService.createCardPerformancesFromFinancial();
        return Response.SUCCESS(savedPerformanceCount);
    }

    @Operation(summary = "카드 실적 조회 API", description = "카드의 실적을 조회합니다.")
    @SwaggerApiSuccess(description = "카드 실적 조회를 성공했습니다.")
    @PostMapping("/get")
    public Response<List<CardPerformance>> getCardPerformances(
            @Parameter(description = "카드 버전 id", example = "3")
            @RequestBody int cardVersionId ) {
        List<CardPerformance> response = cardPerformanceService.getCardPerformances(cardVersionId);
        return Response.SUCCESS(response, "카드 실적 조회를 성공했습니다.");
    }

}