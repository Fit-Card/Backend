package com.fitcard.domain.merchant.merchantinfo.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(hidden = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MerchantInfoSaveAllRequest {

    //가맹점 이름을 ,로 구분해 넣는다.
    @NotEmpty
    private String merchantNames;

    @NotEmpty
    private String category;
}
