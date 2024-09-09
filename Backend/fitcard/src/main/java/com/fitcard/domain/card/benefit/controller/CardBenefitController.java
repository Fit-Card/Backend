package com.fitcard.domain.card.benefit.controller;

import com.fitcard.domain.card.benefit.model.dto.response.CardBenefitGetResponse;
import com.fitcard.domain.card.benefit.service.CardBenefitService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카드 혜택 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards/benefits")
@RequiredArgsConstructor
public class CardBenefitController {

    private final CardBenefitService cardBenefitService;

    @Operation(summary = "카드 혜택 조회 API", description = "카드의 혜택을 조회합니다.")
    @SwaggerApiSuccess(description = "카드 혜택 조회를 성공했습니다.")
    @PostMapping("/get/{id}")
    public Response<CardBenefitGetResponse> getCardBenefit(
            @Parameter(description = "카드 id", example = "3")
            @PathVariable("id") int id) {
        return Response.SUCCESS(null, "사용자 알림 전체 조회를 성공했습니다.");
    }
}
