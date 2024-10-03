package com.financial.domain.bank.usercardpayment.service;

import com.financial.domain.bank.cardperformance.repository.BankCardPerformanceRepository;
import com.financial.domain.bank.usercard.model.BankUserCard;
import com.financial.domain.bank.usercard.repository.BankUserCardRepository;
import com.financial.domain.bank.usercardpayment.exception.BankUserCardPaymentGetException;
import com.financial.domain.bank.usercardpayment.model.BankUserCardPayment;
import com.financial.domain.bank.usercardpayment.model.dto.request.BankUserCardPaymentGetRequest;
import com.financial.domain.bank.usercardpayment.model.dto.response.BankUserCardPaymentGetResponse;
import com.financial.domain.bank.usercardpayment.model.dto.response.BankUserCardPaymentGetResponses;
import com.financial.domain.bank.usercardpayment.repository.BankUserCardPaymentRepository;
import com.financial.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankUserCardPaymentServiceImpl implements BankUserCardPaymentService {

    private final BankUserCardPaymentRepository bankUserCardPaymentRepository;
    private final BankUserCardRepository bankUserCardRepository;
    private final BankCardPerformanceRepository bankCardPerformanceRepository;

    @Override
    public BankUserCardPaymentGetResponses getBankUserCardPayments(BankUserCardPaymentGetRequest request) {

        BankUserCard bankUserCard = bankUserCardRepository.findById(request.getBankUserCardId())
                .orElseThrow(() -> new BankUserCardPaymentGetException(ErrorCode.BAD_REQUEST, "해당하는 사용자 카드가 없습니다."));
        List<BankUserCardPayment> bankUserCardPayments = bankUserCardPaymentRepository.findAllByBankUserCardAndIdGreaterThan(bankUserCard, request.getLastId());
        List<BankUserCardPaymentGetResponse> bankUserCardPaymentGetResponses = bankUserCardPayments.stream()
                .map(BankUserCardPaymentGetResponse::from)
                .toList();

        return BankUserCardPaymentGetResponses.from(bankUserCardPaymentGetResponses);
    }
}
