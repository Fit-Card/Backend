package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자 카드 갱신 정보 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardGetRenewalResponse {

    @Schema(description = "카드 코드", example = "11232342")
    private String cardCode;

    @Schema(description = "카드 종류(가족(0)/개인(1))", example = "1")
    private String cardMemberType;

    @Schema(description = "카드의 해외 결제 카테고리", example = "MASTER")
    private String globalBrand;

    @Schema(description = "카드 이름", example = "신한")
    private String cardName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    @Schema(description = "카드 유효기간", example = "2023-09-09")
    private String expiredDate;
}
