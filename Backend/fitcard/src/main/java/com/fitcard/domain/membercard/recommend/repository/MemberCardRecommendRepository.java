package com.fitcard.domain.membercard.recommend.repository;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.recommend.model.MemberCardRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberCardRecommendRepository extends JpaRepository<MemberCardRecommend, Integer> {

    Optional<MemberCardRecommend> findByMemberCard(MemberCardInfo memberCard);
}
