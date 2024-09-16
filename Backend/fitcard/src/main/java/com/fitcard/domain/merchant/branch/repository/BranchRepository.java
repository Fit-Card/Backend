package com.fitcard.domain.merchant.branch.repository;

import com.fitcard.domain.merchant.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    boolean existsByKakaoLocalId(String kakaoLocalId);
}
