package com.fitcard.domain.membercard.membercardinfo.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 카드 삭제 request dto", description = "사용자 카드 삭제를 위해 정보를 입력합니다.")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCardDeleteRequest {

    @Schema(description = "멤버 카드 id", example = "3")
    @NotNull(message = "멤버 카드 id를 입력하세요.")
    private long memberCardId;
}
