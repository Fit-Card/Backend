package com.fitcard.domain.merchant.merchantinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 이름 조회 응답 DTO", description = "가맹점 이름 조회 결과를 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantSearchNameResponse {

    @Schema(description = "가맹점 아이디", example = "1")
    private Long merchantId;

    @Schema(description = "가맹점 카테고리", example = "FD6")
    private String category;

    @Schema(description = "가맹점 이름", example = "신한카드 본점")
    private String merchantName;

}