package com.fitcard.domain.card.cardinfo.repository;


import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.company.model.CardCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardInfoRepository extends JpaRepository<CardInfo, Integer> {
    List<CardInfo> findByCardCompany(CardCompany cardCompany);

    boolean existsCardInfoByFinancialCardId(String cardId);
}
