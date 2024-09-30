package com.financial.domain.bank.cardbenefit.service;

import com.financial.domain.bank.cardbenefit.model.BankCardBenefit;
import com.financial.domain.bank.cardbenefit.model.BenefitType;
import com.financial.domain.bank.cardbenefit.model.dto.request.BankCardBenefitAddRequest;
import com.financial.domain.bank.cardbenefit.model.dto.response.BankCardBenefitGetResponse;
import com.financial.domain.bank.cardbenefit.model.dto.response.BankCardBenefitGetResponses;
import com.financial.domain.bank.cardbenefit.repository.BankCardBenefitRepository;
import com.financial.domain.bank.cardperformance.model.BankCardPerformance;
import com.financial.domain.bank.cardperformance.repository.BankCardPerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankCardBenefitServiceImpl implements BankCardBenefitService {

    private final BankCardBenefitRepository bankCardBenefitRepository;
    private final BankCardPerformanceRepository bankCardPerformanceRepository;

    @Override
    public void addCardBenefits(List<BankCardBenefitAddRequest> requests) {
        for (BankCardBenefitAddRequest request : requests) {
            BankCardPerformance bankCardPerformance = bankCardPerformanceRepository
                    .findById(request.getCardPerformanceId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid card performance ID"));

            BenefitType benefitType = BenefitType.valueOf(request.getBenefitType());

            BankCardBenefit bankCardBenefit = new BankCardBenefit(
                    bankCardPerformance,
                    benefitType,
                    request.getAmountLimit(),
                    request.getCountLimit(),
                    request.getMinPayment(),
                    request.getBenefitValue(),
                    request.getBenefitPer(),
                    request.getMerchantId(),
                    request.getExceptionTypeList(),
                    request.getMerchantCategory()
            );

            bankCardBenefitRepository.save(bankCardBenefit);  // 각 혜택을 저장
        }
    }

    @Override
    public BankCardBenefitGetResponses getAllBenefits() {
        List<BankCardBenefitGetResponse> bankCardBenefitGetResponses = bankCardBenefitRepository.findAll().stream()
                .map(BankCardBenefitGetResponse::of)
                .toList();

        return BankCardBenefitGetResponses.from(bankCardBenefitGetResponses);
    }

}
