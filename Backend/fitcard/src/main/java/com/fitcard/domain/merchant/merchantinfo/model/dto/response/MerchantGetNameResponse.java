package com.fitcard.domain.merchant.merchantinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 이름 조회 응답 DTO", description = "가맹점 이름 조회 결과를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantGetNameResponse {

    @Schema(description = "가맹점 이름", example = "신한카드 본점")
    private String merchantName;

}