package com.fitcard.infra.kakao.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class KakaoCategoryRectRequestQueryParameter {

    @NotEmpty
    private String category_group_code;

    //rect 정보
    @NotEmpty
    private String topX;

    @NotEmpty
    private String topY;

    @NotEmpty
    private String bottomX;

    @NotEmpty
    private String bottomY;

}
