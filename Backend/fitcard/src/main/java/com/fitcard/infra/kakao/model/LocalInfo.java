package com.fitcard.infra.kakao.model;

import lombok.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalInfo localInfo = (LocalInfo) o;
        return categoryGroupCode.equals(localInfo.categoryGroupCode) && Objects.equals(categoryGroupName, localInfo.categoryGroupName) && Objects.equals(categoryName, localInfo.categoryName) && Objects.equals(phone, localInfo.phone) && placeName.equals(localInfo.placeName) && Objects.equals(placeUrl, localInfo.placeUrl) && Objects.equals(roadAddressName, localInfo.roadAddressName) && x.equals(localInfo.x) && y.equals(localInfo.y) && placeId.equals(localInfo.placeId);
    }

    @Override
    public int hashCode() {
        int result = categoryGroupCode.hashCode();
        result = 31 * result + Objects.hashCode(categoryGroupName);
        result = 31 * result + Objects.hashCode(categoryName);
        result = 31 * result + Objects.hashCode(phone);
        result = 31 * result + placeName.hashCode();
        result = 31 * result + Objects.hashCode(placeUrl);
        result = 31 * result + Objects.hashCode(roadAddressName);
        result = 31 * result + x.hashCode();
        result = 31 * result + y.hashCode();
        result = 31 * result + placeId.hashCode();
        return result;
    }
}
