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
public class KakaoCategoryRequestQueryParameter {

    @NotEmpty
    private String category_group_code;

    @NotEmpty
    private String x;

    @NotEmpty
    private String y;

    @NotNull
    private int radius;

}
