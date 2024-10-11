package com.fitcard.domain.merchantcard.merchantcardinfo.repository;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.MerchantCardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantCardInfoRepository extends JpaRepository<MerchantCardInfo, Integer> {


    @Query("select a " +
            "from MerchantCardInfo a " +
            "where a.merchantId =:merchantInfo ")
    List<MerchantCardInfo> findAllByMerchantId(MerchantInfo merchantInfo);

    @Query(value = "SELECT ci.card_company_id, CONCAT(cc.name, '은행') AS bankname, COUNT(ci.card_company_id) AS count, cc.image_url AS CardCompanyImgUrl " +
            "FROM ( " +
            "   SELECT merchant_id, card_version_id " +
            "   FROM merchant_card " +
            "   WHERE merchant_id = :merchantId " +
            "   UNION " +
            "   SELECT m.merchant_id, cv.id AS card_version_id " +
            "   FROM merchant m " +
            "   LEFT JOIN card_benefit cb ON m.category = cb.merchant_category " +
            "   LEFT JOIN card_performance cp ON cb.card_performance_id = cp.id " +
            "   LEFT JOIN card_version cv ON cp.card_version_id = cv.id " +
            "   WHERE cb.merchant_id BETWEEN 1 AND 6 " +
            "   AND m.merchant_id = :merchantId " +
            "   GROUP BY m.merchant_id, card_version_id " +
            ") AS mc " +
            "LEFT JOIN card_info ci ON mc.card_version_id = ci.id " +
            "LEFT JOIN card_company cc ON ci.card_company_id = cc.id " +
            "GROUP BY ci.card_company_id " +
            "ORDER BY ci.card_company_id",
            nativeQuery = true)
    List<Object[]> findMerchantCardBank(@Param("merchantId") Integer merchantId);

    @Query(value = "SELECT ci.card_company_id, CONCAT(cc.name, '은행') AS bankname, COUNT(ci.card_company_id) AS count, cc.image_url AS CardCompanyImgUrl " +
            "FROM ( " +
            "   SELECT merchant_id, card_version_id " +
            "   FROM merchant_card " +
            "   WHERE merchant_id = :merchantId " +
            "   UNION " +
            "   SELECT m.merchant_id, cv.id AS card_version_id " +
            "   FROM merchant m " +
            "   LEFT JOIN card_benefit cb ON m.category = cb.merchant_category " +
            "   LEFT JOIN card_performance cp ON cb.card_performance_id = cp.id " +
            "   LEFT JOIN card_version cv ON cp.card_version_id = cv.id " +
            "   WHERE cb.merchant_id BETWEEN 1 AND 6 " +
            "   AND m.merchant_id = :merchantId " +
            "   GROUP BY m.merchant_id, card_version_id " +
            ") AS mc " +
            "LEFT JOIN card_info ci ON mc.card_version_id = ci.id " +
            "LEFT JOIN member_card memc ON memc.card_version_id = mc.card_version_id " +
            "LEFT JOIN card_company cc ON ci.card_company_id = cc.id " +
            "where memc.member_id = :loginId " +
            "GROUP BY ci.card_company_id " +
            "ORDER BY ci.card_company_id",
            nativeQuery = true)
    List<Object[]> findMerchantCardBankMy(@Param("loginId") Integer loginId, @Param("merchantId") Integer merchantId);



    @Query(value = "SELECT mc.card_version_id, " +
            "ci.name, " +
            "cv.image_url, " +
            "CASE " +
            "    WHEN cb.merchant_id BETWEEN 1 AND 6 THEN " +
            "        CASE " +
            "            WHEN cb.merchant_id = 1 THEN '편의점 전체' " +
            "            WHEN cb.merchant_id = 2 THEN '주유소 전체' " +
            "            WHEN cb.merchant_id = 3 THEN '문화시설 전체' " +
            "            WHEN cb.merchant_id = 4 THEN '음식점 전체' " +
            "            WHEN cb.merchant_id = 5 THEN '카페 전체' " +
            "            WHEN cb.merchant_id = 6 THEN '가맹점 전체' " +
            "        END " +
            "    ELSE m.name " +
            "END AS merchant_name, " +
            "CASE cb.benefit_type " +
            "    WHEN 'PERCENT_DISCOUNT' THEN CONCAT(cb.benefit_value, '% 할인') " +
            "    WHEN 'PERCENT_REWARD' THEN CONCAT(cb.benefit_value, '% 적립') " +
            "    WHEN 'PERCENT_CASHBACK' THEN CONCAT(cb.benefit_value, '% 캐쉬백') " +
            "    WHEN 'MILEAGE_REWARD' THEN CONCAT(cb.benefit_value, ' 마일리지 적립') " +
            "    WHEN 'AMOUNT_DISCOUNT' THEN CONCAT(cb.benefit_value, '원 할인') " +
            "    WHEN 'POINT_REWARD' THEN CONCAT(cb.benefit_value, ' 포인트 적립') " +
            "    WHEN 'AMOUNT_CASHBACK' THEN CONCAT(cb.benefit_value, '원 캐쉬백') " +
            "    WHEN 'LITER_DISCOUNT' THEN CONCAT('리터당 ', cb.benefit_value, ' 할인') " +
            "    ELSE '기타 혜택' " +
            "END AS benefit_description " +
            "FROM ( " +
            "    SELECT merchant_id, card_version_id " +
            "    FROM merchant_card " +
            "    WHERE merchant_id = :merchantId " +
            "    UNION " +
            "    SELECT m.merchant_id, cv.id AS card_version_id " +
            "    FROM merchant m " +
            "    LEFT JOIN card_benefit cb ON m.category = cb.merchant_category " +
            "    LEFT JOIN card_performance cp ON cb.card_performance_id = cp.id " +
            "    LEFT JOIN card_version cv ON cp.card_version_id = cv.id " +
            "    WHERE cb.merchant_id BETWEEN 1 AND 6 " +
            "    AND m.merchant_id = :merchantId " +
            "    GROUP BY m.merchant_id, card_version_id " +
            ") AS mc " +
            "LEFT JOIN card_version cv ON mc.card_version_id = cv.id " +
            "LEFT JOIN card_performance cp ON cv.id = cp.card_version_id " +
            "LEFT JOIN card_benefit cb ON cp.id = cb.card_performance_id " +
            "LEFT JOIN merchant m ON mc.merchant_id = m.merchant_id " +
            "LEFT JOIN card_info ci ON cv.card_info_id = ci.id " +
            "LEFT JOIN card_company cc ON ci.card_company_id = cc.id " +
            "WHERE ci.card_company_id = :cardCompanyId AND m.name IS NOT NULL and m.category = cb.merchant_category " +
            "GROUP BY mc.card_version_id, ci.name, cv.image_url, merchant_name, cb.benefit_type, cb.benefit_value " +
            "ORDER BY ci.card_company_id",
            nativeQuery = true)
    List<Object[]> findMerchantCardBenefit(@Param("merchantId") Integer merchantId, @Param("cardCompanyId") Integer cardCompanyId);


    @Query(value = "SELECT mc.card_version_id, " +
            "ci.name, " +
            "cv.image_url, " +
            "CASE " +
            "    WHEN cb.merchant_id BETWEEN 1 AND 6 THEN " +
            "        CASE " +
            "            WHEN cb.merchant_id = 1 THEN '편의점 전체' " +
            "            WHEN cb.merchant_id = 2 THEN '주유소 전체' " +
            "            WHEN cb.merchant_id = 3 THEN '문화시설 전체' " +
            "            WHEN cb.merchant_id = 4 THEN '음식점 전체' " +
            "            WHEN cb.merchant_id = 5 THEN '카페 전체' " +
            "            WHEN cb.merchant_id = 6 THEN '가맹점 전체' " +
            "        END " +
            "    ELSE m.name " +
            "END AS merchant_name, " +
            "CASE cb.benefit_type " +
            "    WHEN 'PERCENT_DISCOUNT' THEN CONCAT(cb.benefit_value, '% 할인') " +
            "    WHEN 'PERCENT_REWARD' THEN CONCAT(cb.benefit_value, '% 적립') " +
            "    WHEN 'PERCENT_CASHBACK' THEN CONCAT(cb.benefit_value, '% 캐쉬백') " +
            "    WHEN 'MILEAGE_REWARD' THEN CONCAT(cb.benefit_value, ' 마일리지 적립') " +
            "    WHEN 'AMOUNT_DISCOUNT' THEN CONCAT(cb.benefit_value, '원 할인') " +
            "    WHEN 'POINT_REWARD' THEN CONCAT(cb.benefit_value, ' 포인트 적립') " +
            "    WHEN 'AMOUNT_CASHBACK' THEN CONCAT(cb.benefit_value, '원 캐쉬백') " +
            "    WHEN 'LITER_DISCOUNT' THEN CONCAT('리터당 ', cb.benefit_value, ' 할인') " +
            "    ELSE '기타 혜택' " +
            "END AS benefit_description " +
            "FROM ( " +
            "    SELECT merchant_id, card_version_id " +
            "    FROM merchant_card " +
            "    WHERE merchant_id = :merchantId " +
            "    UNION " +
            "    SELECT m.merchant_id, cv.id AS card_version_id " +
            "    FROM merchant m " +
            "    LEFT JOIN card_benefit cb ON m.category = cb.merchant_category " +
            "    LEFT JOIN card_performance cp ON cb.card_performance_id = cp.id " +
            "    LEFT JOIN card_version cv ON cp.card_version_id = cv.id " +
            "    WHERE cb.merchant_id BETWEEN 1 AND 6 " +
            "    AND m.merchant_id = :merchantId " +
            "    GROUP BY m.merchant_id, card_version_id " +
            ") AS mc " +
            "LEFT JOIN card_version cv ON mc.card_version_id = cv.id " +
            "LEFT JOIN card_performance cp ON cv.id = cp.card_version_id " +
            "LEFT JOIN card_benefit cb ON cp.id = cb.card_performance_id " +
            "LEFT JOIN merchant m ON mc.merchant_id = m.merchant_id " +
            "LEFT JOIN card_info ci ON cv.card_info_id = ci.id " +
            "LEFT JOIN card_company cc ON ci.card_company_id = cc.id " +
            "LEFT JOIN member_card memc ON memc.card_version_id = cv.id " +
            "WHERE ci.card_company_id = :cardCompanyId AND memc.member_id = :loginId AND m.name IS NOT NULL and m.category = cb.merchant_category " +
            "GROUP BY mc.card_version_id, ci.name, cv.image_url, merchant_name, cb.benefit_type, cb.benefit_value " +
            "ORDER BY ci.card_company_id",
            nativeQuery = true)
    List<Object[]> findMerchantCardBenefitMy(@Param("loginId") Integer loginId,@Param("merchantId") Integer merchantId, @Param("cardCompanyId") Integer cardCompanyId);

}
