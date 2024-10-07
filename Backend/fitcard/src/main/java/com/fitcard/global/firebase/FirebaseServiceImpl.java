package com.fitcard.global.firebase;

import com.fitcard.domain.member.model.dto.request.MemberSendJoinFirebaseRequest;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.firebase.dto.FirebaseCardInfoRequest;
import com.fitcard.infra.kakao.model.KakaoCategoryLocalApiResponses;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class FirebaseServiceImpl implements FirebaseService {

    private final RestClient restClient;

    public FirebaseServiceImpl(@Value("${firebase.baseurl}") String firebaseBaseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(firebaseBaseUrl)
                .build();
    }

    @Override
    public void sendJoinRequest(MemberSendJoinFirebaseRequest request) throws FirebaseException{
        Map<String, String> body = new HashMap<>();
        body.put("userId", request.getUserId());
        body.put("fcmToken", request.getFcmToken());
        sendToFirebase("/join", body);
    }

    @Override
    public void addCardsToFirebase(String userId, List<FirebaseCardInfoRequest> cardInfoRequests) throws FirebaseException {
        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);
        body.put("cardInfos", cardInfoRequests); // 카드 정보 리스트를 전송
        
        sendToFirebase2("/addCard", body);
    }

    @Override
    public List<Map<String, String>> getUsersWithCardInfo(String cardCompanyName, String cardName, boolean isBC, boolean isCredit, boolean isPersonal) {
        List<Map<String, String>> targetUserCardInfos = new ArrayList<>();

        try {
            Firestore firestore = FirestoreClient.getFirestore();
            QuerySnapshot querySnapshot = firestore.collection("users").get().get();

            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                List<Map<String, Object>> cardInfos = (List<Map<String, Object>>) document.get("cardInfos");

                if (cardInfos != null) {
                    for (Map<String, Object> cardInfo : cardInfos) {
                        // 카드 정보가 매칭되는 경우
                        if (matchesCardInfo(cardInfo, cardCompanyName, cardName, isBC, isCredit, isPersonal)) {
                            // 해당 유저 ID와 해당 카드의 financialCardId를 저장
                            String userId = document.getId();
                            String financialCardId = (String) cardInfo.get("financialCardId");

                            Map<String, String> userCardInfo = new HashMap<>();
                            userCardInfo.put("userId", userId);
                            userCardInfo.put("financialCardId", financialCardId);

                            targetUserCardInfos.add(userCardInfo);
                            break; // 매칭되는 카드가 있으면 해당 유저는 추가됨
                        }
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Firestore에서 사용자 정보를 가져오는 중 오류 발생", e);
        }

        return targetUserCardInfos; // 유저 ID와 financialCardId를 함께 반환
    }

    private boolean matchesCardInfo(Map<String, Object> cardInfo, String cardCompanyName, String cardName, boolean isBC, boolean isCredit, boolean isPersonal) {
        String storedCardCompanyName = (String) cardInfo.get("cardCompanyName");
        String storedCardName = (String) cardInfo.get("cardName");
        boolean storedIsBC = (boolean) cardInfo.get("bc");
        boolean storedIsCredit = (boolean) cardInfo.get("credit");
        boolean storedIsPersonal = (boolean) cardInfo.get("personal");

        // 카드 정보의 조건이 하나라도 일치하면 true
        return storedCardName.contains(cardName) || (cardCompanyName.equals(storedCardCompanyName) &&
                storedIsBC == isBC &&
                storedIsCredit == isCredit &&
                storedIsPersonal == isPersonal);
    }

    @Override
    public void sendNotificationWithCardInfo(String userId, String title, String body, String financialCardId, Long cardEventId) throws FirebaseException {
        try {
            Firestore firestore = FirestoreClient.getFirestore();

            // Firestore에서 해당 사용자의 FCM 토큰을 가져옴
            String fcmToken = (String) firestore.collection("users")
                    .document(userId)
                    .get()
                    .get()
                    .get("token");

            if (fcmToken == null || fcmToken.isEmpty()) {
                log.error("FCM 토큰이 존재하지 않음: 사용자 ID: {}", userId);
                return;
            }

            // Firebase 메시지 생성 및 전송
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    // 데이터에 financialCardId를 포함
                    .putData("financialCardId", financialCardId)
                    .build();

            // Firebase로 알림 전송
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("알림 전송 성공: {}, 사용자 ID: {}, FinancialCardId: {}", response, userId, financialCardId);

            // Firestore에 알림 기록 저장
            Map<String, Object> notificationData = new HashMap<>();
            notificationData.put("userId", userId);
            notificationData.put("title", title);
            notificationData.put("body", body);
            notificationData.put("financialCardId", financialCardId);
            notificationData.put("timestamp", LocalDateTime.now());
            notificationData.put("cardEventId", cardEventId);

            firestore.collection("notifications")
                    .add(notificationData)
                    .get();

            log.info("알림 기록 저장 완료: 사용자 ID: {}", userId);

        } catch (Exception e) {
            log.error("알림 전송 중 오류 발생: 사용자 ID: {}", userId, e);
            throw new FirebaseException(ErrorCode.FIREBASE_ERROR, "FCM 알림 전송에 실패했습니다.");
        }
    }

    @Override
    public List<Map<String, Object>> getNotifications(Integer memberId) {
        List<Map<String, Object>> notifications = new ArrayList<>();
        log.info("member Id = {}", memberId);
        try {
            Firestore firestore = FirestoreClient.getFirestore();
            QuerySnapshot querySnapshot = firestore.collection("users")
                    .document(String.valueOf(memberId))
                    .collection("notifications")  // 유저별 notifications 컬렉션 접근
                    .get()
                    .get();

            for (QueryDocumentSnapshot document : querySnapshot.getDocuments()) {
                notifications.add(document.getData());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Firestore    에서 알림 정보를 가져오는 중 오류 발생", e);
        }
        return notifications;
    }

    private void sendToFirebase(String uri, Map<String, String> body) throws FirebaseException{
        restClient.post()
                .uri(uri)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    String errorMsg = "Failed to send FCM message with status code: " + response.toString();
                    log.error(errorMsg);
                    throw new FirebaseException(ErrorCode.FIREBASE_ERROR, "firebase에 요청 보내기를 실패했습니다");
                });

//        .exchange((request, response) -> {
//            if (response.getStatusCode().is4xxClientError()) {
//                throw new MyCustomRuntimeException(response.getStatusCode(), response.getHeaders());
//            }
//            else {
//                Pet pet = convertResponse(response);
//                return pet;
//            }
    }

    private void sendToFirebase2(String uri, Map<String, Object> body) throws FirebaseException {
        restClient.post()
                .uri(uri)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    String errorMsg = "Failed to send FCM message with status code: " + response.toString();
                    log.error(errorMsg);
                    throw new FirebaseException(ErrorCode.FIREBASE_ERROR, "firebase에 요청 보내기를 실패했습니다");
                });
    }


}
