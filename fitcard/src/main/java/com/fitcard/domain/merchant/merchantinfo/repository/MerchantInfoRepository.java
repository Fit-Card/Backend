package com.fitcard.domain.merchant.merchantinfo.repository;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantInfoRepository extends JpaRepository<MerchantInfo, Integer> {
}
