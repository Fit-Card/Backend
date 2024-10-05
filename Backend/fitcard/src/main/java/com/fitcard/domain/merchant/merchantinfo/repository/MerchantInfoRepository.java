package com.fitcard.domain.merchant.merchantinfo.repository;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantInfoRepository extends JpaRepository<MerchantInfo, Integer> {

    Optional<MerchantInfo> findByName(String merchantName);

    @Query("SELECT b FROM MerchantInfo b WHERE b.name LIKE CONCAT('%', :keyword, '%')")
    List<MerchantInfo> findMerchantListByMerchantNameKeyword(@Param("keyword") final String keyword);

    MerchantInfo findByMerchantId(Long merchantId);

    @Query("SELECT m.merchantId, cv.id as cardVersionId " +
            "FROM MerchantInfo m " +
            "LEFT JOIN CardBenefit cb ON m.merchantId = cb.merchantId " +
            "LEFT JOIN CardPerformance cp ON cb.cardPerformance.id = cp.id " +
            "LEFT JOIN CardVersion cv ON cp.cardVersion.id = cv.id " +
            "WHERE m.merchantId = :merchantId " +
            "GROUP BY cv.id")
    List<Object[]> findMerchantWithCardVersion(@Param("merchantId") Integer merchantId);

    @Query("SELECT m.merchantId, cv.id as cardVersionId " +
            "FROM MerchantInfo m " +
            "LEFT JOIN CardBenefit cb ON m.merchantId = cb.merchantId " +
            "LEFT JOIN CardPerformance cp ON cb.cardPerformance.id = cp.id " +
            "LEFT JOIN CardVersion cv ON cp.cardVersion.id = cv.id " +
            "WHERE cv.id IS NOT NULL " +
            "GROUP BY m.merchantId, cv.id")
    List<Object[]> findMerchantCard();
}
