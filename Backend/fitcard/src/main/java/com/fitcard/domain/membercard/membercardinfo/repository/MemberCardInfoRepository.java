package com.fitcard.domain.membercard.membercardinfo.repository;

import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardInfoWithCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MemberCardInfoRepository extends JpaRepository<MemberCardInfo, Long> {

    @Query("SELECT CASE WHEN COUNT(mc) > 0 THEN true ELSE false END " +
            "FROM MemberCardInfo mc " +
            "JOIN mc.cardVersion cv " +
            "JOIN cv.cardInfo ci " +
            "WHERE ci.financialCardId = :cardCode")
    boolean existsByFinancialCardId(String cardCode);

    List<MemberCardInfo> findByMember(Member member);

    // JPQL 쿼리: member의 birthDate가 startDate와 endDate 사이에 있는 경우, MemberCardInfo와 count를 함께 조회하여 DTO로 반환
    @Query("SELECT new com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardInfoWithCountDto(" +
            "m.cardVersion.cardInfo, COUNT(m.member.memberId)) " +
            "FROM MemberCardInfo m " +
            "WHERE m.member.birthDate BETWEEN :startDate AND :endDate " +
            "GROUP BY m.cardVersion.cardInfo.id " +
            "ORDER BY COUNT(m.member.memberId) DESC")
    List<MemberCardInfoWithCountDto> findMemberCardInfoByBirthDateRangeWithCardCount(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<MemberCardInfo> findAllByMember(Member member);
}
