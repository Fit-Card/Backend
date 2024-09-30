package com.fitcard.global.firebase;

import com.fitcard.domain.member.model.dto.request.MemberSendJoinFirebaseRequest;

public interface FirebaseService {

    void sendJoinRequest(MemberSendJoinFirebaseRequest request) throws FirebaseException;
}
