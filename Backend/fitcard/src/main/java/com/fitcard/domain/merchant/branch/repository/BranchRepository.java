package com.fitcard.domain.merchant.branch.repository;

import com.fitcard.domain.merchant.branch.model.Branch;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchGetResponses;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByKakaoLocalId(String kakaoLocalId);

    @Query("SELECT b FROM Branch b JOIN b.merchantInfo a WHERE a.name LIKE CONCAT('%', :keyword, '%')")
    List<Branch> findBranchesByMerchantNameKeyword(@Param("keyword") final String keyword);


    @Query("SELECT b, " +
            "trunc((6371000 * acos(cos(radians(:lat)) * cos(radians(b.y)) " +
            "* cos(radians(b.x) - radians(:lon)) + sin(radians(:lat)) * sin(radians(b.y))))) " +
            "FROM Branch b JOIN b.merchantInfo a " +
            "WHERE a.name LIKE CONCAT('%', :keyword, '%') " +
            "ORDER BY trunc((6371000 * acos(cos(radians(:lat)) * cos(radians(b.y)) * cos(radians(b.x) - radians(:lon)) + sin(radians(:lat)) * sin(radians(b.y)))), 0) ")
    List<Object[]> findBranchesByMerchantNameAndLocation(
            @Param("keyword") String keyword,
            @Param("lat") Double latitude,
            @Param("lon") Double longitude,
            Pageable pageable);

    @Query("SELECT " +
            "a, " +
            "b " +
            "FROM Branch b " +
            "left JOIN MerchantInfo a ON a.merchantId = b.merchantInfo.merchantId " +
            "WHERE a.category = :category " +
            "AND b.y BETWEEN :rightLatitude AND :leftLatitude " +
            "AND b.x BETWEEN :leftLongitude AND :rightLongitude " +
            "ORDER BY b.branchName")
    Page<Object[]> findMerchantsWithinRectangle(
            @Param("category") String category,
            @Param("leftLatitude") Double leftLatitude,
            @Param("leftLongitude") Double leftLongitude,
            @Param("rightLatitude") Double rightLatitude,
            @Param("rightLongitude") Double rightLongitude,
            Pageable pageable
    );


    @Query("SELECT DISTINCT b.merchantBranchId," +
            "a, " +
            "b " +
            "FROM Branch b " +
            "left JOIN MerchantInfo a ON a.merchantId = b.merchantInfo.merchantId " +
            "WHERE b.y BETWEEN :rightLatitude AND :leftLatitude " +
            "AND b.x BETWEEN :leftLongitude AND :rightLongitude " +
            "ORDER BY b.branchName")
    Page<Object[]> findMerchantsNoCategoryWithinRectangle(
            @Param("leftLatitude") Double leftLatitude,
            @Param("leftLongitude") Double leftLongitude,
            @Param("rightLatitude") Double rightLatitude,
            @Param("rightLongitude") Double rightLongitude,
            Pageable pageable);

    @Query(value = "SELECT " +
            "mc.card_version_id, " +
            "ci.name, " +
            "cv.image_url, " +
            "m.name, " +
            "max(CASE cb.benefit_type " +
            "WHEN 'PERCENT_DISCOUNT' THEN CONCAT(cb.benefit_value, '% 할인') " +
            "WHEN 'PERCENT_REWARD' THEN CONCAT(cb.benefit_value, '% 적립') " +
            "WHEN 'PERCENT_CASHBACK' THEN CONCAT(cb.benefit_value, '% 캐쉬백') " +
            "WHEN 'MILEAGE_REWARD' THEN CONCAT(cb.benefit_value, ' 마일리지 적립') " +
            "WHEN 'AMOUNT_DISCOUNT' THEN CONCAT(cb.benefit_value, '원 할인') " +
            "WHEN 'POINT_REWARD' THEN CONCAT(cb.benefit_value, ' 포인트 적립') " +
            "WHEN 'AMOUNT_CASHBACK' THEN CONCAT(cb.benefit_value, '원 캐쉬백') " +
            "WHEN 'LITER_DISCOUNT' THEN CONCAT('리터당 ', cb.benefit_value, ' 할인') " +
            "ELSE '기타 혜택' " +
            "END) AS benefit_description " +
            "FROM merchant_branch mb " +
            "left join merchant_card mc on mb.merchant_id = mc.merchant_id " +
            "left join merchant m on mb.merchant_id = m.merchant_id " +
            "left join member_card memc on mc.card_version_id = memc.card_version_id " +
            "left join card_version cv on mc.card_version_id = cv.id " +
            "left join card_info ci on cv.card_info_id = ci.id " +
            "left join member_card_performance mcp on memc.member_card_id = mcp.member_card_id " +
            "left join card_benefit cb on mcp.card_performance_id = cb.card_performance_id " +
            "left join card_performance cp on mcp.card_performance_level = cp.level " +
            "WHERE mb.merchant_branch_id = :merchantBranchId AND memc.member_id = :loginId AND mcp.member_card_performance_id is not null " +
            "and mcp.month = month(now()) " +
            "GROUP BY mc.card_version_id, ci.name, cv.image_url, m.name ", nativeQuery = true)
    List<Object[]> findBranchMemberCardBenefit(@Param("loginId") Integer loginId, @Param("merchantBranchId") Integer merchantBranchId);

    @Query(value = "SELECT " +
            "max(CASE cb.benefit_type " +
            "WHEN 'PERCENT_DISCOUNT' THEN CONCAT(cb.benefit_value, '% 할인') " +
            "WHEN 'PERCENT_REWARD' THEN CONCAT(cb.benefit_value, '% 적립') " +
            "WHEN 'PERCENT_CASHBACK' THEN CONCAT(cb.benefit_value, '% 캐쉬백') " +
            "WHEN 'MILEAGE_REWARD' THEN CONCAT(cb.benefit_value, ' 마일리지 적립') " +
            "WHEN 'AMOUNT_DISCOUNT' THEN CONCAT(cb.benefit_value, '원 할인') " +
            "WHEN 'POINT_REWARD' THEN CONCAT(cb.benefit_value, ' 포인트 적립') " +
            "WHEN 'AMOUNT_CASHBACK' THEN CONCAT(cb.benefit_value, '원 캐쉬백') " +
            "WHEN 'LITER_DISCOUNT' THEN CONCAT('리터당 ', cb.benefit_value, ' 할인') " +
            "ELSE '기타 혜택' " +
            "END) AS benefit_description, round(MAX(:money*(100 - cb.benefit_value)/100), 0) AS benefit " +
            "FROM merchant_branch mb " +
            "left join merchant_card mc on mb.merchant_id = mc.merchant_id " +
            "left join merchant m on mb.merchant_id = m.merchant_id " +
            "left join member_card memc on mc.card_version_id = memc.card_version_id " +
            "left join card_version cv on mc.card_version_id = cv.id " +
            "left join card_info ci on cv.card_info_id = ci.id " +
            "left join member_card_performance mcp on memc.member_card_id = mcp.member_card_id " +
            "left join card_benefit cb on mcp.card_performance_id = cb.card_performance_id " +
            "left join card_performance cp on mcp.card_performance_level = cp.level " +
            "WHERE mb.merchant_branch_id = :merchantBranchId AND memc.member_id = :loginId AND mcp.member_card_performance_id is not null " +
            "and mcp.month = month(now()) and mc.card_version_id = :cardVersionId " +
            "GROUP BY mc.card_version_id, ci.name, cv.image_url, m.name ", nativeQuery = true)
    List<Object[]> calculateBenefit(@Param("loginId") Integer loginId, @Param("merchantBranchId") Integer merchantBranchId, @Param("cardVersionId") Integer cardVersionId, @Param("money") int money);
}
