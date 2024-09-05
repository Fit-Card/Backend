package com.fitcard.domain.membercard.membercardinfo.repository;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCardInfoRepository extends JpaRepository<MemberCardInfo, Integer> {
}
