package com.fitcard.domain.member.service;

import com.fitcard.domain.member.exception.DuplicatedMemberException;
import com.fitcard.domain.member.exception.IncorrectPasswordException;
import com.fitcard.domain.member.exception.InvalidRefreshTokenException;
import com.fitcard.domain.member.exception.MemberNotFoundException;
import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.model.dto.request.MemberLoginRequest;
import com.fitcard.domain.member.model.dto.request.MemberRegisterRequest;
import com.fitcard.domain.member.model.dto.response.MemberLoginResponse;
import com.fitcard.domain.member.model.dto.response.RefreshTokenResponse;
import com.fitcard.domain.member.repository.MemberRepository;
import com.fitcard.global.config.auth.JwtToken;
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
    public void register(MemberRegisterRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new DuplicatedMemberException(ErrorCode.DUPLICATE_MEMBER, "이미 사용 중인 아이디입니다.");
        }

        // 패스워드 인코딩 처리
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        
        // Member 객체 생성 시 인코딩된 패스워드를 전달
        Member member = Member.from(request, encodedPassword);
        memberRepository.save(member);
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

        String accessToken = jwtTokenProvider.createAccessToken(member.getLoginId(), String.valueOf(member.getMemberId()));
        String refreshToken = jwtTokenProvider.createRefreshToken();

        // 클라이언트에게 Access Token과 Refresh Token 모두 반환
        return MemberLoginResponse.of(accessToken, refreshToken, "로그인 성공");
    }

    @Override
    public RefreshTokenResponse refresh(JwtToken jwtToken) {
        String refreshToken = jwtTokenProvider.resolveToken(jwtToken.getRefreshToken());

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsername(jwtToken.getAccessToken());
            String memberId = jwtTokenProvider.getMemberId(jwtToken.getAccessToken());
            String newAccessToken = jwtTokenProvider.createAccessToken(username, memberId);
            return RefreshTokenResponse.from(newAccessToken);
        } else {
            throw new InvalidRefreshTokenException(ErrorCode.INVALID_REFRESH_TOKEN, "유효하지 않은 RefreshToken 토큰입니다.");
        }
    }
}
