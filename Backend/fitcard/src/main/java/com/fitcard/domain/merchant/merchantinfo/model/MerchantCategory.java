package com.fitcard.domain.merchant.merchantinfo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum MerchantCategory {
    CONVENIENCE_STORE(1, "CS2", "편의점"),
    OIL(2, "OL7", "주유소"),
    CULTURE(3, "CT1", "문화시설"),
    FOOD(4, "FD6", "음식점"),
    CAFE(5, "CE7", "카페"),
    ETC(6, "ETC", "기타");

    private final int categoryNum;
    private final String categoryCode;
    private final String categoryName;

    private static final Map<String, MerchantCategory> CATEGORY_MAP = Arrays.stream(MerchantCategory.values())
            .collect(Collectors.toMap(MerchantCategory::getCategoryCode, category -> category));


    public static MerchantCategory getByCategoryCode(String categoryCode) {
        return CATEGORY_MAP.get(categoryCode);
    }
}
