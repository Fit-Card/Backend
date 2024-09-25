package com.financial.domain.bank.usercard.repository;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.fin.user.model.FinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankUserCardRepository extends JpaRepository<BankUserCard, Long> {
    boolean existsByFinUserAndBankCard(FinUser finUser, BankCard bankCard);
}
