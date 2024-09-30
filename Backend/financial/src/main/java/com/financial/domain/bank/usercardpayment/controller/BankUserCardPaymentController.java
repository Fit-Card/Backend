package com.financial.domain.bank.usercardpayment.controller;

import com.financial.domain.bank.usercardpayment.model.dto.request.BankUserCardPaymentGetRequest;
import com.financial.domain.bank.usercardpayment.model.dto.response.BankUserCardPaymentGetResponses;
import com.financial.domain.bank.usercardpayment.service.BankUserCardPaymentService;
import com.financial.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/banks/usercard/payments")
public class BankUserCardPaymentController {

    private final BankUserCardPaymentService bankUserCardPaymentService;

    @GetMapping("/")
    public Response<BankUserCardPaymentGetResponses> getUserCardPayments(@RequestParam Long bankUserCardId,
                                                                             @RequestParam Integer lastId) {
        BankUserCardPaymentGetResponses response = bankUserCardPaymentService.getBankUserCardPayments(new BankUserCardPaymentGetRequest(bankUserCardId, lastId));
        return Response.SUCCESS(response);
    }
}
