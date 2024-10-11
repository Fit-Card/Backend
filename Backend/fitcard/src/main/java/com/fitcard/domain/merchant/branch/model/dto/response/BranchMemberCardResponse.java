package com.fitcard.domain.merchant.branch.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 사용자 카드 조회 응답 DTO", description = "가맹점 사용자 카드 조회 응답 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchMemberCardResponse {
    @Schema(description = "카드 버전 ID", example = "2127")
    private Integer cardVersionId;

    @Schema(description = "카드 이름", example = "신한카드")
    private String cardName;

    @Schema(description = "카드 이미지", example = "https://d1c5n4ri2guedi.cloudfront.net/card/10/card_img/24226/10card.png")
    private String cardImg;

    @Schema(description = "가맹점 이름", example = "스타벅스")
    private String merchantName;

    @Schema(description = "혜택 설명", example = "10%할인")
    private String benefitDescription;

    public static BranchMemberCardResponse of(Integer cardVersionId, String cardName, String cardImg, String merchantName, String benefitDescription){
        return new BranchMemberCardResponse(cardVersionId, cardName, cardImg, merchantName, benefitDescription);
    }
}
