package com.fitcard.domain.alarm.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(name = "알람 상세 조회 request dto", description = "알람 상세 조회에 정보")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AlarmGetRequest {

    @Schema(description = "알람 id", example = "2")
    @NotNull(message = "알람 id를 입력하세요.")
    private Integer alarmId;
}
