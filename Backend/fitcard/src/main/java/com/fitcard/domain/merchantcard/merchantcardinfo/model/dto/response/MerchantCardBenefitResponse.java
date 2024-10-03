package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "", description = "")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardBenefitResponse {
    @Schema(description = "은행사 이름", example = "신한은행")
    private Integer cardVersionId;

    @Schema(description = "카드 이름", example = "신한카드")
    private String cardName;

    @Schema(description = "카드 이미지", example = "https://d1c5n4ri2guedi.cloudfront.net/card/10/card_img/24226/10card.png")
    private String cardImg;

    @Schema(description = "가맹점 이름", example = "스타벅스")
    private String merchantName;

    @Schema(description = "은행사 이름", example = "10%할인")
    private String benefitDescription;

    public static MerchantCardBenefitResponse of(Integer cardVersionId, String cardName, String cardImg, String merchantName, String benefitDescription){
        return new MerchantCardBenefitResponse(cardVersionId, cardName, cardImg, merchantName, benefitDescription);
    }
}