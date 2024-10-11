package com.financial.domain.bank.cardperformance.service;

import com.financial.domain.bank.cardperformance.model.dto.request.BankCardPerformanceAddRequest;
import com.financial.domain.bank.cardperformance.model.dto.response.BankCardPerformanceGetResponses;

public interface BankCardPerformanceService {

    void addCardPerformance(BankCardPerformanceAddRequest request);

    BankCardPerformanceGetResponses getAllPerformances();
}
