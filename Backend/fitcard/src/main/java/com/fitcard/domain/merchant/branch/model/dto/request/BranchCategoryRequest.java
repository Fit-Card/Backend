package com.fitcard.domain.merchant.branch.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "가맹 분점 이름 카테고리 요청 DTO", description = "가맹점 분점리스트 카테고리 필터에 필요한 요청 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BranchCategoryRequest {

//    @Schema(description = "카테고리", example = "FD6")
    @NotEmpty(message = "카테고리를 입력하세요")
    private String category;

    @Schema(description = "왼쪽 위 위도", example = "37.5")
    @NotBlank(message = "위도를 입력하세요")
    private Double leftLatitude;

    @Schema(description = "오른족 아래 위도", example = "37.4")
    @NotBlank(message = "위도를 입력하세요")
    private Double rightLatitude;

    @Schema(description = "왼쪽 위 경도", example = "126.60")
    @NotBlank(message = "경도를 입력하세요")
    private Double leftLongitude;

    @Schema(description = "오른쪽 아래 경도", example = "126.62")
    @NotBlank(message = "경도를 입력하세요")
    private Double rightLongitude;


}
