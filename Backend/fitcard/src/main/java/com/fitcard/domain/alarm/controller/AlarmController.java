package com.fitcard.domain.alarm.controller;

import com.fitcard.domain.alarm.model.dto.request.AlarmGetRequest;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponses;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetResponse;
import com.fitcard.domain.alarm.service.AlarmService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "알림 관련 API")
@RestController
@Slf4j
@RequestMapping("/alarms")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "사용자 알림 전체 조회 API", description = "사용자의 알림을 전체 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 알림 전체 조회를 성공했습니다.")
    @PostMapping("/get/all")
    public Response<AlarmGetAllResponses> getAlarms() {
        return Response.SUCCESS(null, "사용자 알림 전체 조회를 성공했습니다.");
    }

    @Operation(summary = "사용자 알림 상세 조회 API", description = "사용자의 알림을 상세 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 알림 상세 조회를 성공했습니다.")
    @PostMapping("/get/detail")
    public Response<AlarmGetResponse> getAlarm(@RequestBody AlarmGetRequest request) {
        return Response.SUCCESS(null, "사용자 알림 상세 조회를 성공했습니다.");
    }

}
