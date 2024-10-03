package com.fitcard.domain.membercard.payment.model.dto.response;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 카드 실적 현황과 혜택 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPaymentAndBenefitResponse {

    @Schema(description = "카드 id", example = "1")
    private int cardId;

    @Schema(description = "카드 이름", example = "신한 쏠카드")
    private String cardName;

    @Schema(description = "카드 이미지", example = "cardimage.com")
    private String cardImage;

    @Schema(description = "카드 실적 현황")
    private MemberCardPaymentGetStatusResponse memberCardPaymentStatus;

    @Schema(description = "카드의 랜덤 혜택", example = "[\"스타벅스 10% 할인\", \"바나프레소 5% 할인\", \"버거킹 5% 할인]\"")
    private List<String> cardBenefits;

    public static MemberCardPaymentAndBenefitResponse of(CardInfo cardInfo,MemberCardPaymentGetStatusResponse memberCardPaymentStatus, List<String> cardBenefits) {
        return new MemberCardPaymentAndBenefitResponse(cardInfo.getId(), cardInfo.getName(), cardInfo.getCardImage()
                , memberCardPaymentStatus, cardBenefits);
    }


}