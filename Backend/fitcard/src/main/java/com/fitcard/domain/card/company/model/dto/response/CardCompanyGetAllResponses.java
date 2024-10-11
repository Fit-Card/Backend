package com.fitcard.domain.card.company.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(name = "CardCompanyGetAllResponses", description = "카드사 전체 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardCompanyGetAllResponses {

    @Schema(name = "cardCompanyGetResponses", description = "카드사 목록")
    private List<CardCompanyGetResponse> cardCompanyGetResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static CardCompanyGetAllResponses from(List<CardCompanyGetResponse> cardCompanies) {
        return new CardCompanyGetAllResponses(cardCompanies, cardCompanies.size());
    }
}
