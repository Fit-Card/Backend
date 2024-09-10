package com.fitcard.domain.membercard.membercardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "사용자 카드 갱신 정보 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardGetAllRenewalResponses {

    private List<MemberCardGetRenewalResponse> memberCardRenewals;

    @Schema(description = "목록 개수", example = "1")
    private int size;
}