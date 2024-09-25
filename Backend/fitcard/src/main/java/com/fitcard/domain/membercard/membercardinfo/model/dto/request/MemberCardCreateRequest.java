package com.fitcard.domain.membercard.membercardinfo.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Schema(name = "사용자 카드 생성 request dto", description = "사용자 카드 생성을 위해 카드 정보를 입력합니다.")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCardCreateRequest {

    @Schema(description = "member id, 추후 token에서 꺼내도록 변경되면 사용되지 않을 예정입니다.", example = "2")
    @NotNull(message = "member id를 입력하세요.")
    private int memberId;

    @Schema(description = "financial user card id", example = "[2,3,5]")
    @NotNull(message = "financial user card id를 입력하세요.")
    private List<Long> financialUserCardIds;
}
