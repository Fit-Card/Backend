package com.fitcard.domain.member.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(hidden = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberSendJoinFirebaseRequest {

    @NotNull(message = "userId를 입력하세요")
    private String userId;

    @NotBlank(message = "토큰을 입력하세요.")
    private String fcmToken;

    public static MemberSendJoinFirebaseRequest of(MemberSaveFcmTokenRequest request, Integer memberId) {
        return new MemberSendJoinFirebaseRequest(String.valueOf(memberId), request.getFcmToken());
    }
}
