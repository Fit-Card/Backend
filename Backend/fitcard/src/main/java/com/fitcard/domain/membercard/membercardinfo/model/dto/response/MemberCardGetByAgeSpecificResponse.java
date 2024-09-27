package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자의 전체 카드 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardGetByAgeSpecificResponse {

    //카드사 id, 카드사 이름, 카드 id, 카드 이미지, 카드 이름, int로 이용하는 사용자 수

    @Schema(description = "카드 id", example = "1")
    private int cardId;

    @Schema(description = "카드 이름", example = "신한 쏠카드")
    private String cardName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    @Schema(description = "카드사 이름", example = "신한")
    private String cardCompanyName;

    @Schema(description = "카드사 id", example = "1")
    private int cardCompanyId;

    @Schema(description = "사용자 나이대의 사용자 수", example = "27")
    private Long userAgeGroupNumber;

    public static MemberCardGetByAgeSpecificResponse of(MemberCardInfoWithCountDto memberCardInfoWithCountDto) {
        return new MemberCardGetByAgeSpecificResponse(memberCardInfoWithCountDto.getCardInfo().getId(),
                memberCardInfoWithCountDto.getCardInfo().getName(),
                memberCardInfoWithCountDto.getCardInfo().getCardImage(),
                memberCardInfoWithCountDto.getCardInfo().getCardCompany().getName(),
                memberCardInfoWithCountDto.getCardInfo().getCardCompany().getId(),
                memberCardInfoWithCountDto.getCount());
    }
}
