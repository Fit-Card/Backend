package com.fitcard.domain.card.cardinfo.model.dto.response;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "카드사의 전체 카드 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardInfoGetResponse {

    @Schema(description = "카드 id", example = "2")
    private String cardId;

    @Schema(description = "카드 이름", example = "신한")
    private String cardName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    public static CardInfoGetResponse of(CardInfo cardInfo) {
        return new CardInfoGetResponse(String.valueOf(cardInfo.getId()), cardInfo.getName(), cardInfo.getCardImage());
    }

}
