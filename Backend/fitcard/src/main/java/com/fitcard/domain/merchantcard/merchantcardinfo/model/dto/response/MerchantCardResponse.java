package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import com.fitcard.domain.merchantcard.merchantcardinfo.model.MerchantCardInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점 혜택 조회 응답 DTO", description = "가맹점 혜택을 반환하는 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardResponse {
    @Schema(description = "가맹점 ID", example = "95073")
    private Long merchantId;

    @Schema(description = "카드 버전 ID", example = "2")
    private Integer cardVersionId;

    public static MerchantCardResponse from(MerchantCardInfo merchantCardInfo){
        return new MerchantCardResponse(merchantCardInfo.getMerchantId().getMerchantId(), merchantCardInfo.getCardVersionId().getId());
    }
}
