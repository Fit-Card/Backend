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
    private String categoryGroupCode;

    @NotNull
    private double minLon;

    @NotNull
    private double minLat;

    @NotNull
    private double maxLon;

    @NotNull
    private double maxLat;

}
