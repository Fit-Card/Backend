package com.financial.domain.bank.card.repository;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, String> {

    List<BankCard> findByFinCardCompany(FinCardCompany cardCompany);
}
