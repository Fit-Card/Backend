package com.fitcard.domain.membercard.performance.repository;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.performance.model.MemberCardPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCardPerformanceRepository extends JpaRepository<MemberCardPerformance, Integer> {

    Optional<MemberCardPerformance> findByMemberCardAndYearAndMonth(MemberCardInfo memberCardInfo, int year, int month);
}
