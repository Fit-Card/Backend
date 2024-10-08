package com.fitcard.domain.membercard.recommend.model.dto.response;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.recommend.model.MemberCardRecommend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 카드의 추천 카드 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardRecommendResponse {

    @Schema(description = "사용자 카드 id", example = "1")
    private long memberCardId;

    @Schema(description = "사용자 카드 이름", example = "쏠쏠 카드")
    private String memberCardName;

    @Schema(description = "사용자 카드 이미지 url", example = "image.url")
    private String memberCardImageUrl;

    @Schema(description = "사용자 카드 할인 내역", example = "350")
    private int memberCardBenefitAmount;

    @Schema(description = "추천 카드 performance id", example = "1")
    private int recommendCardPerformanceId;

    @Schema(description = "추천 카드 이름", example = "신한 쏠카드")
    private String recommendCardName;

    @Schema(description = "추천 카드 이미지", example = "cardimage.com")
    private String recommendCardImage;

    @Schema(description = "추천 카드 할인 내역, 이 값은 사용자 카드 할인 내역보다 작을 수 있습니다.", example = "350")
    private int recommendCardBenefitAmount;

    public static MemberCardRecommendResponse empty(MemberCardInfo memberCard) {
        return new MemberCardRecommendResponse(memberCard.getMemberCardId(),memberCard.getCardVersion().getCardInfo().getName(), memberCard.getCardVersion().getImageUrl(),
                0,0,"","",0);
    }


    public static MemberCardRecommendResponse of(MemberCardRecommend memberCardRecommend) {
        return new MemberCardRecommendResponse(memberCardRecommend.getMemberCard().getMemberCardId(),memberCardRecommend.getMemberCard().getCardVersion().getCardInfo().getName(), memberCardRecommend.getMemberCard().getCardVersion().getImageUrl(),
                memberCardRecommend.getMemberCardBenefitAmount(), memberCardRecommend.getRecommendCardPerformance().getId(),
                memberCardRecommend.getRecommendCardPerformance().getCardVersion().getCardInfo().getName(),
                memberCardRecommend.getRecommendCardPerformance().getCardVersion().getImageUrl(),
                memberCardRecommend.getRecommendCardBenefitAmount());
    }
}
