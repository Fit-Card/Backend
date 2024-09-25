package com.fitcard.domain.membercard.membercardinfo.repository;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberCardInfoRepository extends JpaRepository<MemberCardInfo, Integer> {

    @Query("SELECT CASE WHEN COUNT(mc) > 0 THEN true ELSE false END " +
            "FROM MemberCardInfo mc " +
            "JOIN mc.cardVersion cv " +
            "JOIN cv.cardInfo ci " +
            "WHERE ci.financialCardId = :cardCode")
    boolean existsByFinancialCardId(String cardCode);
}
