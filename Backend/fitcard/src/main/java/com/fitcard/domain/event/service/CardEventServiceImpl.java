package com.fitcard.domain.event.service;

import com.fitcard.domain.event.model.CardEvent;
import com.fitcard.domain.event.repository.CardEventRepository;
import com.fitcard.global.firebase.FirebaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CardEventServiceImpl implements CardEventService {

    private final CardEventRepository cardEventRepository;
    private final FirebaseService firebaseService;

    @Override
    public boolean detectNewCardEvents(){
        // 24시간 이내에 추가된 이벤트를 감지
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        List<CardEvent> newCardEvents = cardEventRepository.findNewEvents(cutoffTime);


        if (newCardEvents.isEmpty()) {
            log.info("새로운 카드 이벤트가 없습니다.");
            return false;
        }
        // 새로운 이벤트가 감지되면 알림 전송
        sendNotificationForNewEvents(newCardEvents);
        return true;
    }

    @Override
    public void sendNotificationForNewEvents(List<CardEvent> newCardEvents) {

    }

}
