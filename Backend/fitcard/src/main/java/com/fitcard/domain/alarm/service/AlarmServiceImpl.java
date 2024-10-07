package com.fitcard.domain.alarm.service;

import com.fitcard.domain.alarm.exception.AlarmNotFoundException;
import com.fitcard.domain.alarm.model.Alarm;
import com.fitcard.domain.alarm.model.dto.request.AlarmGetRequest;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponse;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetAllResponses;
import com.fitcard.domain.alarm.model.dto.response.AlarmGetResponse;
import com.fitcard.domain.card.cardinfo.exception.CardInfoNotFoundException;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.event.exception.CardEventNotFoundException;
import com.fitcard.domain.event.repository.CardEventRepository;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final FirebaseService firebaseService;
    private final CardInfoRepository cardInfoRepository;
    private final CardEventRepository cardEventRepository;

    @Override
    public AlarmGetAllResponses getAlarms(Integer memberId) {
        // Firestore에서 해당 회원의 알림을 조회
        List<Map<String, Object>> notifications = firebaseService.getNotifications(memberId);

        List<AlarmGetAllResponse> alarmGetAllResponses = new ArrayList<>();

        // 각 알림 정보를 AlarmGetAllResponse로 변환
        for (Map<String, Object> notification : notifications) {
            Long cardEventId = (Long) notification.get("cardEventId");
            String cardName = cardInfoRepository.findByFinancialCardId((String) notification.get("financialCardId")).get().getName();
            String cardImage = cardInfoRepository.findByFinancialCardId((String) notification.get("financialCardId")).get().getCardImage();
            String alarmTitle = (String) notification.get("title");
            String alarmCreatedAt = notification.get("timestamp").toString();

            AlarmGetAllResponse alarmResponse = AlarmGetAllResponse.of(
                    cardEventId,
                    cardName,
                    cardImage,
                    alarmTitle,
                    alarmCreatedAt
            );
            alarmGetAllResponses.add(alarmResponse);
        }

        return AlarmGetAllResponses.from(alarmGetAllResponses);
    }

    @Override
    public AlarmGetResponse getAlarmsDetail(Integer memberId, AlarmGetRequest request) {
        Long cardEventId = request.getCardEventId();

        // Firestore에서 해당 회원의 알림을 조회
        List<Map<String, Object>> notifications = firebaseService.getNotifications(memberId);
        Map<String, Object> notification = notifications.stream()
                .filter(notifi -> cardEventId.equals(notifi .get("cardEventId")))
                .findFirst()
                .orElseThrow(() -> new AlarmNotFoundException(ErrorCode.ALARM_NOT_FOUND, "알람이 존재하지 않습니다."));

        // 카드 정보 조회
        String financialCardId = (String) notification.get("financialCardId");
        var cardInfo = cardInfoRepository.findByFinancialCardId(financialCardId)
                .orElseThrow(() -> new CardInfoNotFoundException(ErrorCode.CARD_NOT_FOUND, "카드 정보가 존재하지 않습니다."));

        // 알람 정보
        String cardName = cardInfo.getName();
        String cardImage = cardInfo.getCardImage();
        String alarmTitle = (String) notification.get("title");
        String alarmContent = (String) notification.get("body"); // 알림 내용(body 필드)
        String alarmCreatedAt = notification.get("timestamp").toString();

        // 카드 이벤트 테이블에서 이벤트 정보 조회
        var cardEvent = cardEventRepository.findById(cardEventId)
                .orElseThrow(() -> new CardEventNotFoundException(ErrorCode.CARD_EVENT_NOT_FOUND, "카드 이벤트 정보가 존재하지 않습니다."));

        // 이벤트 정보
        String eventStartDate = cardEvent.getStartDate().toString();  // 이벤트 시작일
        String eventEndDate = cardEvent.getEndDate().toString();      // 이벤트 종료일
        String eventUrl = cardEvent.getEventUrl();                    // 이벤트 URL
        String targetCards = cardEvent.getTarget();

        return AlarmGetResponse.of(cardName,
                cardImage,
                alarmTitle,
                alarmContent,
                targetCards,
                eventStartDate,
                eventEndDate,
                eventUrl,
                alarmCreatedAt);
    }
}
