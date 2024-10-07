package com.fitcard.domain.event.service;

import com.fitcard.domain.alarm.model.Alarm;
import com.fitcard.domain.alarm.repository.AlarmRepository;
import com.fitcard.domain.event.model.CardEvent;
import com.fitcard.domain.event.repository.CardEventRepository;
import com.fitcard.global.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CardEventServiceImpl implements CardEventService {

    private final CardEventRepository cardEventRepository;
    private final FirebaseService firebaseService;
    private final AlarmRepository alarmRepository;

    @Override
    public boolean detectNewCardEvents() {
        // 24시간 이내에 추가된 이벤트를 감지
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        List<CardEvent> newCardEvents = cardEventRepository.findNewEvents(cutoffTime);

        if (newCardEvents.isEmpty()) {
            log.info("새로운 카드 이벤트가 없습니다.");
            return false;
        }

        for (CardEvent cardEvent : newCardEvents) {
            // 알람 생성
            Alarm alarm = Alarm.of(
                    cardEvent,
                    cardEvent.getCardCompany(),
                    cardEvent.getTitle(),
                    cardEvent.getContent()
            );

            alarmRepository.save(alarm);
        }

        // 새로운 이벤트가 감지되면 알림 전송
        sendNotificationForNewEvents(newCardEvents);
        return true;
    }

    @Override
    public void sendNotificationForNewEvents(List<CardEvent> newCardEvents) {
        // 이벤트마다 알림 전송
        for (CardEvent cardEvent : newCardEvents) {
            log.info("새로운 카드 이벤트: {} 에 대한 알림 전송 시작", cardEvent);

            // Firebase에서 카드 정보와 매칭되는 사용자 목록을 가져옴
            List<Map<String, String>> targetUserCardInfos = firebaseService.getUsersWithCardInfo(
                    cardEvent.getCardCompany().getName(),
                    cardEvent.getTarget(),
                    cardEvent.isBC(),
                    cardEvent.isCredit(),
                    cardEvent.getIsPersonal()
            );


            if (targetUserCardInfos.isEmpty()) {
                log.info("이벤트와 관련된 사용자가 없습니다: {}", cardEvent);
                continue;
            }

            // 각 사용자에게 알림 전송
            for (Map<String, String> userCardInfo : targetUserCardInfos) {
                String userId = userCardInfo.get("userId");
                String financialCardId = userCardInfo.get("financialCardId");
                Long cardEventId = cardEvent.getCardEventId();

                String messageTitle = cardEvent.getTitle();
                String messageBody = cardEvent.getTarget();

                // FCM을 통해 알림 전송
                firebaseService.sendNotificationWithCardInfo(userId, messageTitle, messageBody, financialCardId, cardEventId);
                log.info("알림 전송 완료: 사용자 ID: {}, 이벤트 ID: {}", userId, cardEvent.getCardEventId());
            }
        }
    }
}