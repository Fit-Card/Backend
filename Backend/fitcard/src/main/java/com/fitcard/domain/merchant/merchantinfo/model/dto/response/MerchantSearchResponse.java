package com.fitcard.domain.merchant.merchantinfo.model.dto.response;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 키워드 검색 조회 응답 DTO", description = "가맹점 검색 결과를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantSearchResponse {
    @Schema(description = "가맹점 ID", example = "FD6")
    private Long merchantId;

    @Schema(description = "가맹점 카테고리", example = "FD6")
    private String category;

    @Schema(description = "가맹점 이름", example = "신한카드 본점")
    private String name;

    public static MerchantSearchResponse from(MerchantInfo merchantInfo){
        return new MerchantSearchResponse(merchantInfo.getMerchantId(), merchantInfo.getCategory(), merchantInfo.getName());
    }
}
