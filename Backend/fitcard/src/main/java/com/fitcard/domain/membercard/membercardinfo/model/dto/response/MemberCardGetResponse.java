package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자의 전체 카드 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardGetResponse {

    @Schema(description = "카드 이름", example = "신한")
    private String cardName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    public static MemberCardGetResponse from(MemberCardInfo memberCardInfo) {
        return new MemberCardGetResponse(memberCardInfo.getCardVersion().getCardInfo().getName(),
                memberCardInfo.getCardVersion().getImageUrl());
    }
}
