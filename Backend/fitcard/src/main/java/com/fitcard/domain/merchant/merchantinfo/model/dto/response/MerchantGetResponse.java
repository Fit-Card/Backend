package com.fitcard.domain.merchant.merchantinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 id 조회 응답 DTO", description = "가맹점 결과를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantGetResponse {

    @Schema(description = "가맹점 카테고리", example = "FD6")
    private String category;

    @Schema(description = "가맹점 이름", example = "신한카드 본점")
    private String name;

    public static MerchantGetResponse of(String category, String name){
        return new MerchantGetResponse(category, name);
    }
}
