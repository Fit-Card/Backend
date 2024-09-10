package com.fitcard.infra.kakao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoCategoryLocalApiResponses {

    private List<KakaoCategoryLocalApiResponse> documents;
    private Meta meta;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoCategoryLocalApiResponse {
        private String address_name;
        private String category_group_code;
        private String category_group_name;
        private String category_name;
        private String distance;
        private String id;
        private String phone;
        private String place_name;
        private String place_url;
        private String road_address_name;
        private String x;
        private String y;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private boolean is_end;
        private int pageable_count;
        private int total_count;
    }
}