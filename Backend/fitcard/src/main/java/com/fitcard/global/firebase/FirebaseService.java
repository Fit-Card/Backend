package com.fitcard.global.firebase;

import com.fitcard.domain.member.model.dto.request.MemberSendJoinFirebaseRequest;
import com.fitcard.global.firebase.dto.FirebaseCardInfoRequest;

import java.util.List;


public interface FirebaseService {

    void sendJoinRequest(MemberSendJoinFirebaseRequest request) throws FirebaseException;

    void addCardsToFirebase(String userId, List<FirebaseCardInfoRequest> cardInfoRequests) throws FirebaseException;
}
