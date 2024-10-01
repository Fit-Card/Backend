package com.fitcard.domain.merchant.merchantinfo.repository;

import com.fitcard.domain.merchant.branch.model.Branch;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MerchantInfoRepository extends JpaRepository<MerchantInfo, Integer> {

    Optional<MerchantInfo> findByName(String merchantName);

    @Query("SELECT b FROM MerchantInfo b WHERE b.name LIKE CONCAT('%', :keyword, '%')")
    List<MerchantInfo> findMerchantListByMerchantNameKeyword(@Param("keyword") final String keyword);
}
