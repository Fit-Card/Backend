package com.financial.domain.bank.cardbenefit.controller;

import com.financial.domain.bank.cardbenefit.model.dto.request.BankCardBenefitAddRequest;
import com.financial.domain.bank.cardbenefit.service.BankCardBenefitService;
import com.financial.global.config.swagger.SwaggerApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카드사 카드 혜택 관련 API")
@RestController
@Slf4j
@RequestMapping("/bank/cards/benefits")
@RequiredArgsConstructor
public class BankCardBenefitController {

    private final BankCardBenefitService bankCardBenefitService;

    @Operation(summary = "카드사 카드 혜택 추가 API", description = "카드사의 카드 혜택을 추가합니다.")
    @SwaggerApiSuccess(description = "카드사 카드 혜택 추가를 성공했습니다.")
    @PostMapping("/add")
    public ResponseEntity<String> addCardBenefit(@Valid @RequestBody List<BankCardBenefitAddRequest> bankCardBenefitAddRequests){
        bankCardBenefitService.addCardBenefits(bankCardBenefitAddRequests);
        return ResponseEntity.ok("카드사 카드 혜택 추가가 완료되었습니다.");
    }

}
