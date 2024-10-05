package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹점 혜택 조회 요청 DTO", description = "가맹점 혜택 조회를 위한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MerchantCardBenefitRequest {

    @Schema(description = "가맹점 ID", example = "95174")
    @NotNull(message = "가맹점 ID는 필수 항목입니다.")
    private Integer merchantId;

    @Schema(description = "은행사 ID", example = "1")
    @NotNull(message = "은행사 ID는 필수 항목입니다.")
    private Integer cardCompanyId;

    @Schema(description = "내카드만 여부", example = "0")
    @NotNull(message = "내카드만 여부는 필수 항목입니다.")
    private int isMine;
}
