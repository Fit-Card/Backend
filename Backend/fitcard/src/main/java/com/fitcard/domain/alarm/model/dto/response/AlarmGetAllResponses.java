package com.fitcard.domain.alarm.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Schema(name = "AlarmGetAllResponses", description = "알람 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmGetAllResponses {

    @Schema(name = "alarmResponses", description = "알람 목록")
    private List<AlarmGetAllResponse> alarmResponses;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static AlarmGetAllResponses from(List<AlarmGetAllResponse> alarmResponses) {
        return new AlarmGetAllResponses(alarmResponses, alarmResponses.size());
    }
}
