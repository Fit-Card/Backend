package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import com.fitcard.domain.card.benefit.model.dto.response.CardBenefitGetSimpleResponses;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.membercard.payment.model.dto.response.MemberCardPaymentGetStatusResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 카드 실적 현황과 혜택 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPerformanceAndBenefitResponse {

    @Schema(description = "카드 id", example = "1")
    private int cardId;

    @Schema(description = "카드 이름", example = "신한 쏠카드")
    private String cardName;

    @Schema(description = "카드 이미지", example = "cardimage.com")
    private String cardImage;

    @Schema(description = "카드 실적 현황")
    private MemberCardPaymentGetStatusResponse memberCardPaymentStatus;

    @Schema(description = "카드의 랜덤 혜택")
    private CardBenefitGetSimpleResponses cardBenefits;

    public static MemberCardPerformanceAndBenefitResponse of(CardInfo cardInfo, MemberCardPaymentGetStatusResponse memberCardPaymentStatus, CardBenefitGetSimpleResponses cardBenefits) {
        return new MemberCardPerformanceAndBenefitResponse(cardInfo.getId(), cardInfo.getName(), cardInfo.getCardImage()
                , memberCardPaymentStatus, cardBenefits);
    }


}
