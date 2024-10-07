package com.fitcard.global.firebase;

import com.fitcard.domain.member.model.dto.request.MemberSendJoinFirebaseRequest;
import com.fitcard.global.firebase.dto.FirebaseCardInfoRequest;

import java.util.List;
import java.util.Map;


public interface FirebaseService {

    void sendJoinRequest(MemberSendJoinFirebaseRequest request) throws FirebaseException;

    void addCardsToFirebase(String userId, List<FirebaseCardInfoRequest> cardInfoRequests) throws FirebaseException;

    // 사용자 카드 정보를 기준으로 알림을 받을 사용자 목록을 가져오는 메서드
    List<Map<String, String>> getUsersWithCardInfo(String cardCompanyName, String cardName, boolean isBC, boolean isCredit, boolean isPersonal);

    // FCM을 통해 특정 사용자에게 알림을 전송하는 메서드
    void sendNotificationWithCardInfo(String userId, String title, String body, String financialCardId, Long cardEventId) throws FirebaseException;

    // 멤버 아이디로 알람 조회
    List<Map<String, Object>> getNotifications(Integer memberId) throws FirebaseException;
}
