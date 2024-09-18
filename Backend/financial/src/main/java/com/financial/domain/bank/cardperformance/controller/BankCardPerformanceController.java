package com.financial.domain.bank.cardperformance.controller;

import com.financial.domain.bank.cardperformance.model.dto.request.BankCardPerformanceAddRequest;
import com.financial.domain.bank.cardperformance.service.BankCardPerformanceService;
import com.financial.global.config.swagger.SwaggerApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카드사 카드 실적 관련 API")
@RestController
@Slf4j
@RequestMapping("/bank/cards/performance")
@RequiredArgsConstructor
public class BankCardPerformanceController {

    private final BankCardPerformanceService bankCardPerformanceService;

    @Operation(summary = "카드사 카드 실적 추가 API", description = "카드사의 카드 실적을 추가합니다.")
    @SwaggerApiSuccess(description = "카드사 카드 실적 추가를 성공했습니다.")
    @PostMapping("/add")
    public ResponseEntity<String> addCardPerformance(@Valid @RequestBody BankCardPerformanceAddRequest request){
        bankCardPerformanceService.addCardPerformance(request);
        return ResponseEntity.ok("카드사 카드 실적 추가가 완료되었습니다.");
    }

}
