package com.fitcard.domain.merchantcard.map.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Schema(name = "가맹점 혜택 조회 응답 DTO", description = "가맹점에서 받을 수 있는 혜택 정보 응답")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MapGetBenefitsResponse {
    @Schema(description = "카드 이름", example = "신한카드 처음")
    private String cardName;

    @Schema(description = "혜택 설명", example = "5% 할인")
    private String benefitDescription;

    @Schema(description = "카드 이미지", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImage;
}
