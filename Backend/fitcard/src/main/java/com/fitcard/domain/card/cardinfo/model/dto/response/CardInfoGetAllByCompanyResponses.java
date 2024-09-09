package com.fitcard.domain.card.cardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "카드사별 카드 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardInfoGetAllByCompanyResponses {

    @Schema(description = "카드 조회 목록")
    private List<CardInfoGetResponse> alarmResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;
}
