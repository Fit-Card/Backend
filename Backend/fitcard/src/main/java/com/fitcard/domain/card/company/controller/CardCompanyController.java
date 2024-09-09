package com.fitcard.domain.card.company.controller;

import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponses;
import com.fitcard.domain.card.company.model.dto.response.CardCompanyGetAllResponses;
import com.fitcard.domain.card.company.service.CardCompanyService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카드사 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards/companies")
@RequiredArgsConstructor
public class CardCompanyController {
    private final CardCompanyService cardCompanyService;

    @Operation(summary = "카드사 전체 조회 API", description = "모든 카드사 목록을 조회합니다.")
    @SwaggerApiSuccess(description = "카드사 전체 조회를 성공했습니다.")
    @PostMapping("/get/all")
    public Response<CardCompanyGetAllResponses> getCardCompanies() {
        return Response.SUCCESS(null, "사용자 알림 전체 조회를 성공했습니다.");
    }
}
