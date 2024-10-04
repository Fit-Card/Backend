package com.financial.domain.bank.usercardpayment.service;

import com.financial.domain.bank.usercardpayment.model.dto.request.BankUserCardPaymentGetRequest;
import com.financial.domain.bank.usercardpayment.model.dto.response.BankUserCardPaymentGetResponses;

public interface BankUserCardPaymentService {

    BankUserCardPaymentGetResponses getBankUserCardPayments(BankUserCardPaymentGetRequest request);
}
