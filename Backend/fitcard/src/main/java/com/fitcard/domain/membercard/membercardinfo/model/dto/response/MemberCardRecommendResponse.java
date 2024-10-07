package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 카드의 추천 카드 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardRecommendResponse {

    @Schema(description = "카드 id", example = "1")
    private int cardId;

    @Schema(description = "카드 이름", example = "신한 쏠카드")
    private String cardName;

    @Schema(description = "카드 이미지", example = "cardimage.com")
    private String cardImage;

    @Schema(description = "카드 혜택 차이, 값이 음수라면 사용자의 카드보다 좋은 혜택을 가진 카드가 없다는 뜻입니다.", example = "1300")
    private int benefitDifference;

}
