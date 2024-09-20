package com.fitcard.domain.member.service;

import com.fitcard.domain.member.exception.DuplicatedMemberException;
import com.fitcard.domain.member.exception.IncorrectPasswordException;
import com.fitcard.domain.member.exception.InvalidRefreshTokenException;
import com.fitcard.domain.member.exception.MemberNotFoundException;
import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.model.dto.request.MemberLoginRequest;
import com.fitcard.domain.member.model.dto.request.MemberRegisterRequest;
import com.fitcard.domain.member.model.dto.response.MemberLoginResponse;
import com.fitcard.domain.member.model.dto.response.MemberRegisterResponse;
import com.fitcard.domain.member.model.dto.response.RefreshTokenResponse;
import com.fitcard.domain.member.repository.MemberRepository;
import com.fitcard.global.config.auth.JwtTokenProvider;
import com.fitcard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberRegisterResponse register(MemberRegisterRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new DuplicatedMemberException(ErrorCode.DUPLICATE_MEMBER, "이미 사용 중인 아이디입니다.");
        }

        // Member 객체를 of 메서드로 생성
        Member member = Member.of(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getBirthDate(),
                request.getPhoneNumber(),
                false,
                ""
        );

        memberRepository.save(member);
        return MemberRegisterResponse.of(member.getLoginId(), "회원가입 성공");
    }

    @Override
    public boolean checkDuplicatedId(String userId) {
        return memberRepository.existsByLoginId(userId);
    }

    @Override
    public MemberLoginResponse login(MemberLoginRequest request) {
        Member member = memberRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND,"존재하지 않는 ID 입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IncorrectPasswordException(ErrorCode.INCORRECT_PASSWORD, "아이디와 비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getLoginId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getLoginId());

        // 클라이언트에게 Access Token과 Refresh Token 모두 반환
        return MemberLoginResponse.of(accessToken, refreshToken, "로그인 성공");
    }

    @Override
    public RefreshTokenResponse refresh(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(refreshToken);
            String newAccessToken = jwtTokenProvider.createAccessToken(username);
            return RefreshTokenResponse.of(newAccessToken);
        } else {
            throw new InvalidRefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN, "유효하지 않은 RefreshToken 토큰입니다.");
        }
    }
}
