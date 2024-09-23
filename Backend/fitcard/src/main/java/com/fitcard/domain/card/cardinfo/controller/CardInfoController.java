package com.fitcard.domain.card.cardinfo.controller;

import com.fitcard.domain.card.cardinfo.model.dto.response.CardInfoGetResponses;
import com.fitcard.domain.card.cardinfo.service.CardInfoService;
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

@Tag(name = "카드 정보 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardInfoController {

    private final CardInfoService cardInfoService;

    @Operation(summary = "카드사의 전체 카드 조회 API", description = "카드사의 전체 카드 목록을 조회합니다. 사전 순 정렬입니다.")
    @SwaggerApiSuccess(description = "카드사의 전체 카드 조회를 성공했습니다.")
    @PostMapping("/get/{cardCompanyId}")
    public Response<CardInfoGetResponses> getCardsByCompany(
            @Parameter(description = "카드사 id", example = "3")
            @PathVariable("cardCompanyId") int cardCompanyId) {
        CardInfoGetResponses response = cardInfoService.getCardsByCompany(cardCompanyId);
        return Response.SUCCESS(response, "카드사의 전체 카드 조회를 성공했습니다.");
    }

}
