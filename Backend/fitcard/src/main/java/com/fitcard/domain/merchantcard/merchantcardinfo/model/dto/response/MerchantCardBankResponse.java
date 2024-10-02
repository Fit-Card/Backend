package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.company.model.CardCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "", description = "")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardBankResponse {
    @Schema(description = "은행사 ID", example = "1")
    private Integer cardCompanyId;

    @Schema(description = "은행사 이름", example = "신한은행")
    private String bankName;

    @Schema(description = "은행사 개수", example = "6")
    private int count;

    public static MerchantCardBankResponse from(Integer cardCompanyId, String bankName, int count){
        return new MerchantCardBankResponse(cardCompanyId, bankName, count);
    }
}