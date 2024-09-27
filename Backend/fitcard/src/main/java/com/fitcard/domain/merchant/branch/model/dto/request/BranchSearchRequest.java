package com.fitcard.domain.merchant.branch.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹 분점 이름 검색 요청 DTO", description = "가맹점 분점리스트 검색에 필요한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BranchSearchRequest {

    @Schema(description = "검색 이름", example = "파리")
    @NotBlank(message = "검색 내용을 입력하세요")
    private String merchantNameKeyword;

    @Schema(description = "위도", example = "37.501550")
    @NotBlank(message = "위도를 입력하세요")
    private Double latitude;

    @Schema(description = "경도", example = "127.039528")
    @NotBlank(message = "경도를 입력하세요")
    private Double longitude;
}
