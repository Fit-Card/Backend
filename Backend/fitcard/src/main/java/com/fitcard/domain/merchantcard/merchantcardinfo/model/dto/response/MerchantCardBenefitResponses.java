package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponse;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.PrivateKey;
import java.util.List;

@Schema(description = "가맹점별 혜택 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardBenefitResponses {

    @Schema(description = "혜택 목록")
    private List<MerchantCardBenefitResponse> merchantCardBenefitResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static MerchantCardBenefitResponses from(List<MerchantCardBenefitResponse> merchantCardBenefitResponses){
        return new MerchantCardBenefitResponses(merchantCardBenefitResponses, merchantCardBenefitResponses.size());
    }
}
