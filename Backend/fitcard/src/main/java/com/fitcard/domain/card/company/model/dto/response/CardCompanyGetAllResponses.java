package com.fitcard.domain.card.company.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "카드사 전체 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardCompanyGetAllResponses {
    private List<CardCompanyGetAllResponse> cardCompanies;
}
