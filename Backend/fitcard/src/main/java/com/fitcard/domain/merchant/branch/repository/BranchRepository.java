package com.fitcard.domain.merchant.branch.repository;

import com.fitcard.domain.merchant.branch.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    boolean existsByKakaoLocalId(String kakaoLocalId);

    @Query("SELECT b FROM Branch b WHERE b.merchantInfo.merchantId = :merchant_Id")
    List<Branch> findBranchesByMerchantBranchId(@Param("merchant_Id") final Long merchantId);
}
