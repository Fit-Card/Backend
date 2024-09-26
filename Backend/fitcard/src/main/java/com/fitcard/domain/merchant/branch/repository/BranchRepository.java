package com.fitcard.domain.merchant.branch.repository;

import com.fitcard.domain.merchant.branch.model.Branch;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchGetResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT b FROM Branch b JOIN b.merchantInfo a WHERE a.name LIKE CONCAT('%', :keyword, '%')")
    List<Branch> findBranchesByMerchantNameKeyword(@Param("keyword") final String keyword);

    @Query("SELECT b FROM Branch b JOIN b.merchantInfo a " +
            "WHERE a.name LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(b.y)) " +
            "* cos(radians(b.x) - radians(:lon)) + sin(radians(:lat)) * sin(radians(b.y)))) ASC")
    Page<Branch> findBranchesByMerchantNameAndLocation(@Param("keyword") final String keyword, @Param("lat") Double latitude, @Param("lon") Double longitude, Pageable pageable);
}
