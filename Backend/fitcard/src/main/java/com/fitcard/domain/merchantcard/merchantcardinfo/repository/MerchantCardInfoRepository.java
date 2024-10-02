package com.fitcard.domain.merchantcard.merchantcardinfo.repository;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.MerchantCardInfo;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardBankResponse;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantCardInfoRepository extends JpaRepository<MerchantCardInfo, Integer> {


    @Query("select a " +
            "from MerchantCardInfo a " +
            "where a.merchantId =:merchantInfo ")
    List<MerchantCardInfo> findAllByMerchantId(MerchantInfo merchantInfo);

    @Query("SELECT ci.cardCompany.id as cardCompanyId, " +
            "concat(cc.name, '은행') as bankName, " +
            "count(ci.cardCompany.id) as count " +
            "FROM MerchantCardInfo mc " +
            "LEFT JOIN mc.cardVersionId cv " +
            "LEFT JOIN cv.cardInfo ci " +
            "LEFT JOIN ci.cardCompany cc " +
            "WHERE mc.merchantId.merchantId = :merchantId " +
            "GROUP BY ci.cardCompany.id " +
            "ORDER BY ci.cardCompany.id")
    List<Object[]> findMerchantCardBank(@Param("merchantId") Integer merchantId);
}
