package com.fitcard.domain.merchant.merchantinfo.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹점 이름 검색 요청 DTO", description = "가맹점 검색에 필요한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MerchantSearchRequest {

    @Schema(description = "검색 이름", example = "파리")
    @NotBlank(message = "검색 내용을 입력하세요")
    private String merchantNameKeyword;
}
