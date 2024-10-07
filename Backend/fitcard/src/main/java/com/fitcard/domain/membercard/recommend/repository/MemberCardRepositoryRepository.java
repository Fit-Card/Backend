package com.fitcard.domain.membercard.recommend.repository;

import com.fitcard.domain.membercard.recommend.model.MemberCardRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberCardRepositoryRepository extends JpaRepository<MemberCardRecommend, Integer> {

}
