package com.fitcard.domain.card.company.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Schema(name = "카드사 전체 조회 응답 dto", description = "카드사 전체 조회 응답 DTO")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardCompanyGetResponse {

    @Schema(description = "카드사 id", example = "2")
    private String companyId;

    @Schema(description = "카드사 이름", example = "신한")
    private String companyName;

    @Schema(description = "카드사 로고 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String companyImageUrl;
}
