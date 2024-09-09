package com.fitcard.domain.membercard.membercardinfo.model.dto.request;

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
public class MemberCardCreateRequest {

    @Schema(description = "카드 코드", example = "11232342")
    @NotNull(message = "카드 코드를 입력하세요.")
    private String cardCode;

    @Schema(description = "카드 종류(가족(0)/개인(1))", example = "1")
    @NotNull(message = "카드 종류를 입력하세요.")
    private String cardMemberType;

    @Schema(description = "카드의 해외 결제 카테고리", example = "MASTER")
    @NotNull(message = "해외 결제 카테고리를 입력하세요.")
    private String globalBrand;
}
