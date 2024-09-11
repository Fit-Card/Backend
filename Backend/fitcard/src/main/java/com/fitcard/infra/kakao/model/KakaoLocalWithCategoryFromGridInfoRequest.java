package com.fitcard.infra.kakao.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class KakaoLocalWithCategoryFromGridInfoRequest {

    @NotEmpty
    private String category_group_code;

    @NotNull
    private int radius;

    @NotNull
    private double minLat;

    @NotNull
    private double minLon;

    @NotNull
    private double maxLat;

    @NotNull
    private double maxLon;

}
