package com.fitcard.domain.merchantcard.repository;

import com.fitcard.domain.merchantcard.model.MerchantCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantCardRepository extends JpaRepository<MerchantCard, Integer> {
}
