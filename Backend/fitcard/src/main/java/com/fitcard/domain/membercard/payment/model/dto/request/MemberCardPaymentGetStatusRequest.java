package com.fitcard.domain.membercard.payment.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 카드 생성 request dto", description = "사용자 카드 생성을 위해 카드 정보를 입력합니다.")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCardPaymentGetStatusRequest {

    @Schema(description = "member card id", example = "2")
    @NotNull(message = "member card id를 입력하세요.")
    private long memberCardId;
}
