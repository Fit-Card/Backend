package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "카드사 목록")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardBankResponses {

    private List<MerchantCardBankResponse> merchantCardCardCompanyResponses;

    public static MerchantCardBankResponses from(List<MerchantCardBankResponse> merchantCardBankResponses){
        return new MerchantCardBankResponses(merchantCardBankResponses);
    }
}
