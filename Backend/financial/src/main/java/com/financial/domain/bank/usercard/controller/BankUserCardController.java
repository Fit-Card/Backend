package com.financial.domain.bank.usercard.controller;

import com.financial.domain.bank.usercard.model.dto.request.BankUserCardDeleteRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardSaveRequest;
import com.financial.domain.bank.usercard.service.BankUserCardService;
import com.financial.domain.fin.cardcompany.model.dto.response.CardCompanyGetResponses;
import com.financial.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/banks/usercard")
public class BankUserCardController {

    private final BankUserCardService bankUserCardService;

    @PostMapping("/")
    public Response<?> saveUserCard(@RequestBody BankUserCardSaveRequest request) {
        bankUserCardService.saveUserCard(request);
        return Response.SUCCESS();
    }

    @DeleteMapping("/")
    public Response<?> deleteUserCard(@RequestBody BankUserCardDeleteRequest request) {
        bankUserCardService.deleteUserCard(request);
        return Response.SUCCESS();
    }
}
