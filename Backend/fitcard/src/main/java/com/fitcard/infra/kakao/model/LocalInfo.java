package com.fitcard.infra.kakao.model;

import lombok.*;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocalInfo {

    private String categoryGroupCode;
    private String categoryGroupName;
    private String categoryName;
    private String phone;
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private String x;
    private String y;
    private String placeId;

    public static LocalInfo from(KakaoCategoryLocalApiResponses.KakaoCategoryLocalApiResponse response) {
        return new LocalInfo(response.getCategory_group_code(),
                response.getCategory_group_name(),
                response.getCategory_name(),
                response.getPhone(),
                response.getPlace_name(),
                response.getPlace_url(),
                response.getRoad_address_name(),
                response.getX(),
                response.getY(),
                response.getId());
    }
}
