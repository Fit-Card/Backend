package com.fitcard.domain.alarm.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(description = "알람 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmGetAllResponses {

    @Schema(description = "알람 목록")
    List<AlarmGetAllResponse> alarmResponses;

    public static AlarmGetAllResponses from(List<AlarmGetAllResponse> alarmResponses) {
        return new AlarmGetAllResponses(alarmResponses);
    }
}
