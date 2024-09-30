package com.fitcard.global.firebase;

import com.fitcard.domain.member.model.dto.request.MemberSendJoinFirebaseRequest;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.infra.kakao.model.KakaoCategoryLocalApiResponses;
import java.util.HashMap;
import java.util.Map;
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
}
