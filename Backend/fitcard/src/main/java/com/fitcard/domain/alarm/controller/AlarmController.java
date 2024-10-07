package com.fitcard.domain.alarm.controller;

import com.fitcard.domain.alarm.model.dto.request.AlarmGetRequest;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponses;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetResponse;
import com.fitcard.domain.alarm.service.AlarmService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.firebase.FirebaseService;
import com.fitcard.global.guard.Login;
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

    private final FirebaseService firebaseService;
    private final AlarmService alarmService;

    @Operation(summary = "사용자 알림 전체 조회 API", description = "사용자의 알림을 전체 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 알림 전체 조회를 성공했습니다.")
    @PostMapping("/get/all")
    public Response<AlarmGetAllResponses> getAlarms(@Login Integer memberId) {
        AlarmGetAllResponses response = alarmService.getAlarms(memberId);
        return Response.SUCCESS(response, "사용자 알림 전체 조회를 성공했습니다.");
    }

    @Operation(summary = "사용자 알림 상세 조회 API", description = "사용자의 알림을 상세 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 알림 상세 조회를 성공했습니다.")
    @PostMapping("/get/detail")
    public Response<AlarmGetResponse> getAlarm(@Login Integer memberId, @RequestBody AlarmGetRequest request) {
        AlarmGetResponse response = alarmService.getAlarmsDetail(memberId, request);
        return Response.SUCCESS(null, "사용자 알림 상세 조회를 성공했습니다.");
    }

}
