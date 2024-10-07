package com.fitcard.domain.alarm.service;

import com.fitcard.domain.alarm.model.dto.request.AlarmGetRequest;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponses;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetResponse;

public interface AlarmService {
    AlarmGetAllResponses getAlarms(Integer memberId);

    AlarmGetResponse getAlarmsDetail(Integer memberId, AlarmGetRequest request);
}
