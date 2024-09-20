package com.fitcard.domain.member.service;

import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.model.dto.request.MemberUpdateRequest;
import com.fitcard.domain.member.model.dto.response.MemberGetResponse;
import com.fitcard.domain.member.model.dto.response.MemberUpdateResponse;
import com.fitcard.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberGetResponse getUser(String loginId) {
        // 로그인한 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자 정보 반환
        return MemberGetResponse.of(member);
    }

    @Override
    public MemberUpdateResponse updateUser(String loginId, MemberUpdateRequest request) {
        // 로그인한 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 사용자 비밀번호 암호화 후 업데이트
        String encryptedPassword = passwordEncoder.encode(request.getNewPassword());

        // 사용자 정보 수정
        member.update(encryptedPassword, request.getNewPhoneNumber());
        memberRepository.save(member);

        // 수정된 사용자 정보 반환
        return MemberUpdateResponse.of(member);
    }

}
