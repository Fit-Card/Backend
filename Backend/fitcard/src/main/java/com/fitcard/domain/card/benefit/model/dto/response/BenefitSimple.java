package com.fitcard.domain.card.benefit.model.dto.response;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantCategory;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(name = "BenefitSimple", description = "간단한 혜택 정보")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BenefitSimple {

    @Schema(description = "가맹점 이름", example = "버거킹")
    private String merchantName;

    @Schema(description = "가맹점 카테고리", example = "음식점")
    private String merchantCategory;

    @Schema(description = "할인 혜택 정보", example = "10% 할인")
    private String discountInfo;


    public static BenefitSimple of(MerchantInfo merchantInfo, String discountInfo) {
        return new BenefitSimple(merchantInfo.getName(), MerchantCategory.getByCategoryCode(merchantInfo.getCategory()).getCategoryName(), discountInfo);
    }

}
