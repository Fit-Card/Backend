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

}
