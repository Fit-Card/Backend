package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCardInfoWithCountDto {

    private CardInfo cardInfo;
    private Long count;

}