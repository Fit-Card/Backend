package com.fitcard.domain.card.performance.controller;

import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.card.performance.service.CardPerformanceService;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}