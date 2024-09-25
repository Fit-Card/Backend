package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.company.model.CardCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 카드 갱신 정보 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MemberCardGetRenewalResponse {

    @Schema(description = "카드 코드", example = "11232342")
    private String cardCode;

    @Schema(description = "카드 이름", example = "신한")
    private String cardName;

    @Schema(description = "카드사 id", example = "1")
    private Integer cardCompanyId;

    @Schema(description = "카드사 이름", example = "신한")
    private String cardCompanyName;

    @Schema(description = "카드 이미지 url", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImageUrl;

    @Schema(description = "카드 유효기간", example = "2023-09")
    private String expiredDate;

    @Schema(description = "financial 사용자 카드 id", example = "1")
    private Long userCardId;

    public static MemberCardGetRenewalResponse of(CardInfo cardInfo, CardCompany cardCompany, String expiredDate, Long  userCardId) {
        return new MemberCardGetRenewalResponse(cardInfo.getFinancialCardId(),
                cardInfo.getName(),
                cardCompany.getId(),
                cardCompany.getName(),
                cardInfo.getCardImage(),
                expiredDate,
                userCardId);
    }
}
