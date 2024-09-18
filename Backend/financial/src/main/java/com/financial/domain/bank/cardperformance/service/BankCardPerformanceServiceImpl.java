package com.financial.domain.bank.cardperformance.service;

import com.financial.domain.bank.card.model.BankCard;
import com.financial.domain.bank.cardperformance.model.BankCardPerformance;
import com.financial.domain.bank.cardperformance.model.dto.request.BankCardPerformanceAddRequest;
import com.financial.domain.bank.cardperformance.repository.BankCardPerformanceRepository;
import com.financial.domain.bank.card.repository.BankCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankCardPerformanceServiceImpl implements BankCardPerformanceService {

    private final BankCardPerformanceRepository bankCardPerformanceRepository;
    private final BankCardRepository bankCardRepository;

    @Override
    public void addCardPerformance(BankCardPerformanceAddRequest request) {
        // 카드 ID로 BankCard 객체 조회
        BankCard bankCard = bankCardRepository.findById(request.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid card ID: " + request.getCardId()));

        // BankCardPerformance 엔티티 생성
        BankCardPerformance bankCardPerformance = new BankCardPerformance(
                bankCard,
                request.getLevel(),
                request.getAmount()
        );

        // 데이터베이스에 저장
        bankCardPerformanceRepository.save(bankCardPerformance);
    }
}
