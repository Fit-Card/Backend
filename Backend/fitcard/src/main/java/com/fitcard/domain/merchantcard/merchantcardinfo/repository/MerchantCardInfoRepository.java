package com.fitcard.domain.merchantcard.merchantcardinfo.repository;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.MerchantCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantCardInfoRepository extends JpaRepository<MerchantCardInfo, Integer> {


    @Query("select a " +
            "from MerchantCardInfo a " +
            "where a.merchantId =:merchantInfo ")
    List<MerchantCardInfo> findAllByMerchantId(MerchantInfo merchantInfo);
}
