package com.fitcard.domain.merchant.merchantinfo.model.dto.response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "가맹점 검색 리스트")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantSearchResponses {
    private List<MerchantSearchResponse> merchantResponses;

    public static MerchantSearchResponses from(List<MerchantSearchResponse> merchantResponses) {
        return new MerchantSearchResponses(merchantResponses);
    }
}
