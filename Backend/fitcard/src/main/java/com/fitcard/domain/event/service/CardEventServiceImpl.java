package com.fitcard.domain.event.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fitcard.domain.alarm.model.Alarm;
import com.fitcard.domain.alarm.repository.AlarmRepository;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import com.fitcard.domain.event.exception.SaveEventsFromFinancialException;
import com.fitcard.domain.event.model.CardEvent;
import com.fitcard.domain.event.model.dto.response.FinancialCardEventResponses;
import com.fitcard.domain.event.repository.CardEventRepository;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.firebase.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class CardEventServiceImpl implements CardEventService {

    private final CardEventRepository cardEventRepository;
    private final FirebaseService firebaseService;
    private final AlarmRepository alarmRepository;
    private final String FINANCIAL_GET_EVENTS;
    private final RestClient restClient;
    private final CardCompanyRepository cardCompanyRepository;

    public CardEventServiceImpl(CardEventRepository cardEventRepository, FirebaseService firebaseService,
                                @Value("${financial.card.card-event.get-all}") String FINANCIAL_GET_EVENTS, AlarmRepository alarmRepository, CardCompanyRepository cardCompanyRepository) {
        this.cardEventRepository = cardEventRepository;
        this.firebaseService = firebaseService;
        this.alarmRepository = alarmRepository;
        this.FINANCIAL_GET_EVENTS = FINANCIAL_GET_EVENTS;
        this.restClient = RestClient.create();
        this.cardCompanyRepository = cardCompanyRepository;
    }

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
                    cardEvent
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
                    cardEvent.getIsPersonal(),
                    cardEvent.getIsCategory()
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

    @Override
    public int createCardEventsFromFinancial(){
        String response = restClient.get()
                .uri(FINANCIAL_GET_EVENTS)
                .retrieve()
                .body(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        FinancialCardEventResponses financialCardEventResponses;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");
            log.info(dataNode.toString());
            financialCardEventResponses = objectMapper.treeToValue(dataNode, FinancialCardEventResponses.class);
            log.info("financialCardEventResponses = {}", financialCardEventResponses);
        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", e.getMessage());
            throw new SaveEventsFromFinancialException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }

        List<CardEvent> cardEvents = financialCardEventResponses.getBankCardEventGetResponses().stream()
                .map(financialCardEventResponse -> {
                    String cardCompanyId = financialCardEventResponse.getCardCompanyId();
                    CardCompany cardCompany = cardCompanyRepository.findById(Integer.parseInt(cardCompanyId)).get();
                    String target = financialCardEventResponse.getTarget();
                    boolean isCategory = true;
                    boolean isBC = true;
                    boolean isCredit = true;
                    boolean isPersonal = true;

                    if (target.contains("비씨")){
                        isBC = false;
                    }

                    if (target.contains("체크")) {
                        isCredit = false;
                    }

                    if (target.contains("가족")) {
                        isPersonal = false;
                    }

                    target = extractSpecificCard(target);

                    if(target.equals("All")) {
                        isCategory = false;
                    }

                    return CardEvent.of(cardCompany, financialCardEventResponse, target, isCategory, isCredit, isBC, isPersonal);
                })
                .toList();

        return cardEventRepository.saveAll(cardEvents).size();
    }

    public String extractSpecificCard(String target) {

        // 카드 이름 목록
        List<String> cardNames = Arrays.asList(
                "Visa", "Master", "WE:SH", "트래블러스 체크카드", "프라임", "플래티늄", "JCB", "비자",
                "유니온페이", "나라사랑 체크카드", "비씨카드", "체크카드", "신용카드", "사장님카드", "쿠팡 와우",
                "쿠쿠렌탈Ⅱ카드", "KB Pay", "마에스트로카드"
        );

        // 괄호 안의 내용 제거
        String cleanedTarget = target.replaceAll("\\(.*?\\)", "").trim();

        // 카드 이름이 포함되어 있는지 확인
        for (String cardName : cardNames) {
            if (cleanedTarget.contains(cardName)) {
                return target; // 특정 카드 이름이 있으면 해당 카드 이름 반환
            }
        }

        return "All"; // 특정 카드가 없으면 All 반환
    }

}