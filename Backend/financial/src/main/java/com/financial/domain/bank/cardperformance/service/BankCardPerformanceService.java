package com.financial.domain.bank.cardperformance.service;

import com.financial.domain.bank.cardperformance.model.dto.request.BankCardPerformanceAddRequest;

public interface BankCardPerformanceService {

    void addCardPerformance(BankCardPerformanceAddRequest request);
}
