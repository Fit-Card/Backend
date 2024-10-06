package com.fitcard.domain.merchant.branch.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹 분점 사용자 카드 리스트 요청 DTO", description = "가맹 분점 사용자 카드 리스트 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BranchMemberCardRequest {
    @Schema(description = "가맹분점 ID", example = "179940")
    @NotEmpty(message = "가맹분점 ID를 입력하세요")
    private Integer merchantBranchId;
}
