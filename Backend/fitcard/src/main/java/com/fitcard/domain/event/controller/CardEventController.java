package com.fitcard.domain.event.controller;


import com.fitcard.domain.event.scheduler.CardEventScheduler;
import com.fitcard.domain.event.service.CardEventService;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카드 이벤트 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards/event")
@RequiredArgsConstructor
public class CardEventController {

    private final CardEventScheduler cardEventScheduler;
    private final CardEventService cardEventService;

    // 스케줄링 작업을 수동으로 실행하는 API
    @PostMapping("/run-scheduler")
    public ResponseEntity<String> runScheduler() {
        cardEventScheduler.detectNewCardEvents();  // 스케줄러의 메서드 호출

        return ResponseEntity.ok("카드 이벤트 감지 작업이 실행되었습니다.");
    }

    @PostMapping("/import")
    public Response<Integer> createCardEventsFromFinancial(){
        int savedEventCount = cardEventService.createCardEventsFromFinancial();
        return Response.SUCCESS(savedEventCount);
    }

}
