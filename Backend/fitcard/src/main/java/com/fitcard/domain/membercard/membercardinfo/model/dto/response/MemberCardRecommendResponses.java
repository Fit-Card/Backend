package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "사용자 카드의 추천 카드 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MemberCardRecommendResponses {

    private List<MemberCardRecommendResponse> memberCardRecommendResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static MemberCardRecommendResponses from(List<MemberCardRecommendResponse> memberCardRecommendResponses) {
        return new MemberCardRecommendResponses(memberCardRecommendResponses, memberCardRecommendResponses.size());
    }
}
