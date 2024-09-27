package com.fitcard.domain.card.benefit.controller;

import com.fitcard.domain.card.benefit.model.dto.response.CardBenefitGetResponse;
import com.fitcard.domain.card.benefit.service.CardBenefitService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import com.fitcard.infra.CardGorillaWebClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "카드 혜택 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards/benefits")
@RequiredArgsConstructor
public class CardBenefitController {

    private final CardBenefitService cardBenefitService;
    private final CardGorillaWebClientService cardGorillaWebClientService;

    @Operation(summary = "카드 혜택 조회 API", description = "카드의 혜택을 조회합니다.")
    @SwaggerApiSuccess(description = "카드 혜택 조회를 성공했습니다.")
    @PostMapping("/get/{id}")
    public Response<CardBenefitGetResponse> getCardBenefit(
            @Parameter(description = "카드 id", example = "3")
            @PathVariable("id") int id) {
        return Response.SUCCESS(null, "사용자 알림 전체 조회를 성공했습니다.");
    }
    
    @GetMapping("/fetch")
    public Response<String> fetchCardData() {
            for (int cardId = 1; cardId <= 1; cardId++) {
                cardGorillaWebClientService.fetchCardBenefit(cardId);
            }
            return Response.SUCCESS(null, "카드 정보 조회가 완료되었습니다.");

    }

    @PostMapping("/import")
    public Response<Integer> createCardBenefitsFromFinancial(){
        int savedBenefitCount = cardBenefitService.createCardBenefitsFromFinancial();
        return Response.SUCCESS(savedBenefitCount);
    }

}
