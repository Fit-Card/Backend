package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "가맹점별 혜택 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardBenefitResponses {

    private List<MerchantCardBenefitResponse> merchantCardBenefitResponse;

    public static MerchantCardBenefitResponses from(List<MerchantCardBenefitResponse> MerchantCardBenefitResponse){
        return new MerchantCardBenefitResponses(MerchantCardBenefitResponse);
    }
}
