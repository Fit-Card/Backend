package com.fitcard.domain.member.service;

import com.fitcard.domain.member.model.dto.request.MemberLoginRequest;
import com.fitcard.domain.member.model.dto.request.MemberRegisterRequest;
import com.fitcard.domain.member.model.dto.response.MemberLoginResponse;
import com.fitcard.domain.member.model.dto.response.RefreshTokenResponse;

public interface AuthService {
    void register(MemberRegisterRequest request);  // 회원가입 메서드

    boolean checkDuplicatedId(String userId);  // 아이디 중복 확인 메서드

    MemberLoginResponse login(MemberLoginRequest request);  // 로그인 메서드

    RefreshTokenResponse refresh(String refreshToken);
}
