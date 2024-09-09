package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "카드사의 전체 카드 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardGetResponse {

    @Schema(description = "카드 id", example = "2")
    private String cardId;

    @Schema(description = "카드사 이름", example = "신한")
    private String cardName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    @Schema(description = "카드 유효기간", example = "2023-09-09")
    private String expiredDate;
}
