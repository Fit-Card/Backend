package com.fitcard.domain.merchant.merchantinfo.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹점 이름 조회 요청 DTO", description = "가맹점 이름을 조회할 때 필요한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MerchantGetNameRequest {
    @Schema(description = "가맹점 ID", example = "1")
    @NotNull(message = "가맹점 ID는 필수 입력 항목입니다.")
    private Long merchantId;
}
