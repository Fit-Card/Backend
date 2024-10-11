package com.fitcard.domain.member.repository;

import com.fitcard.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    // loginId로 회원을 조회하는 메서드
    Optional<Member> findByLoginId(String loginId);
    // loginId의 중복 여부 확인 메서드
    boolean existsByLoginId(String loginId);

    boolean existsByLoginIdOrPhoneNumber(String loginId, String phoneNumber);
}
