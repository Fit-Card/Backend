package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 나이대 카드 사용순 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardGetByAgeSpecificResponses {

    private List<MemberCardGetByAgeSpecificResponse> memberCardGetByAgeSpecificResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static MemberCardGetByAgeSpecificResponses from(List<MemberCardGetByAgeSpecificResponse> memberCardGetByAgeSpecificResponses) {
        return new MemberCardGetByAgeSpecificResponses(memberCardGetByAgeSpecificResponses, memberCardGetByAgeSpecificResponses.size());
    }
}
