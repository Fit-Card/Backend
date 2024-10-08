package com.fitcard.domain.event.scheduler;

import com.fitcard.domain.event.service.CardEventService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardEventScheduler {

    private final CardEventService cardEventService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void detectNewCardEvents(){
        log.info("스케쥴링: 새로운 카드 이벤트 감지 작업 시작");

        // 새로 추가된 카드 이벤트를 감지
        boolean isNewEventAdded = cardEventService.detectNewCardEvents();

        if (!isNewEventAdded) {
            log.info("새로운 카드 이벤트가 없습니다.");
        }
    }

}
