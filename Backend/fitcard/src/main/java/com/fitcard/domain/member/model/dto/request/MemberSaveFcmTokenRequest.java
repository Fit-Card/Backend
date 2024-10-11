package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "사용자 FCM 토큰 생성 DTO", description = "이벤트 알림을 위한 FCM 토큰 발급 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberSaveFcmTokenRequest {

    @Schema(description = "FCM 토큰", example = "askjdfhkjhsdfjkkqkjw")
    @NotBlank(message = "토큰을 입력하세요.")
    private String fcmToken;
}
