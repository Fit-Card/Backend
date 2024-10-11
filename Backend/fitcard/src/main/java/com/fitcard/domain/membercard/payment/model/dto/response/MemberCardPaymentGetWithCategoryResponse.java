package com.fitcard.domain.membercard.payment.model.dto.response;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Schema(description = "사용자 카드 카테고리별 결제내역 조회 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCardPaymentGetWithCategoryResponse {

    @Schema(description = "음식점 지출 금액", example = "150000")
    private Integer restaurant;

    @Schema(description = "카페 지출 금액", example = "100000")
    private Integer cafe;

    @Schema(description = "편의점 지출 금액", example = "50000")
    private Integer convenienceStores;

    @Schema(description = "문화 지출 금액", example = "250000")
    private Integer culture;

    @Schema(description = "주유소 지출 금액", example = "150000")
    private Integer gasStations;

    @Schema(description = "기타 지출 금액", example = "100000")
    private Integer other;

    @Schema(description = "지출 금액 총액", example = "700000")
    private Integer totalAmount;

    public static MemberCardPaymentGetWithCategoryResponse from(Map<String, Integer> paymentsByCategory){
        // 카테고리별 금액 추출, 만약 값이 없으면 0으로 처리
        int restaurantAmount = paymentsByCategory.getOrDefault(MerchantCategory.FOOD.getCategoryName(), 0);
        int cafeAmount = paymentsByCategory.getOrDefault(MerchantCategory.CAFE.getCategoryName(), 0);
        int convenienceStoresAmount = paymentsByCategory.getOrDefault(MerchantCategory.CONVENIENCE_STORE.getCategoryName(), 0);
        int cultureAmount = paymentsByCategory.getOrDefault(MerchantCategory.CULTURE.getCategoryName(), 0);
        int gasStationsAmount = paymentsByCategory.getOrDefault(MerchantCategory.OIL.getCategoryName(), 0);
        int otherAmount = paymentsByCategory.getOrDefault(MerchantCategory.ETC.getCategoryName(), 0);

        // 전체 금액 계산
        int totalAmount = restaurantAmount + cafeAmount + convenienceStoresAmount + cultureAmount + gasStationsAmount + otherAmount;

        return new MemberCardPaymentGetWithCategoryResponse(
                restaurantAmount,
                cafeAmount,
                convenienceStoresAmount,
                cultureAmount,
                gasStationsAmount,
                otherAmount,
                totalAmount
        );
    }

}
