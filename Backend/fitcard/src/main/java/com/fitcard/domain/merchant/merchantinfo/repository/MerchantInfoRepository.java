package com.fitcard.domain.merchant.merchantinfo.repository;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantInfoRepository extends JpaRepository<MerchantInfo, Integer> {

    Optional<MerchantInfo> findByName(String merchantName);
}
