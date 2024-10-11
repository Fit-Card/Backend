package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "사용자의 전체 카드 조회 DTO")
@Getter
public class MemberCardGetResponse {

    @Schema(description = "사용자 카드 id", example = "1")
    private Long id;

    @Schema(description = "카드 이름", example = "신한")
    private String cardName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    public MemberCardGetResponse(Long id, String cardName, String cardImageUrl) {
        this.id = id;
        this.cardName = cardName;
        this.cardImageUrl = cardImageUrl;
    }

    public static MemberCardGetResponse from(MemberCardInfo memberCardInfo) {
        return new MemberCardGetResponse(memberCardInfo.getMemberCardId(),
                memberCardInfo.getCardVersion().getCardInfo().getName(),
                memberCardInfo.getCardVersion().getImageUrl());
    }
}
