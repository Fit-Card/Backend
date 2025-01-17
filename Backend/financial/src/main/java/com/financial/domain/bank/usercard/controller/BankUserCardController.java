package com.financial.domain.bank.usercard.controller;

import com.financial.domain.bank.usercard.model.dto.request.BankUserCardDeleteRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardGetAllRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardGetRequest;
import com.financial.domain.bank.usercard.model.dto.request.BankUserCardSaveRequest;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponse;
import com.financial.domain.bank.usercard.model.dto.response.BankUserCardGetResponses;
import com.financial.domain.bank.usercard.service.BankUserCardService;
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
    public Response<?> createUserCard(@RequestBody BankUserCardSaveRequest request) {
        bankUserCardService.createUserCard(request);
        return Response.SUCCESS();
    }

    @DeleteMapping("/")
    public Response<?> deleteUserCard(@RequestBody BankUserCardDeleteRequest request) {
        bankUserCardService.deleteUserCard(request);
        return Response.SUCCESS();
    }

    @GetMapping("/all/{userId}")
    public Response<BankUserCardGetResponses> getAllUserCards(@PathVariable String userId) {
        BankUserCardGetResponses response = bankUserCardService.getAllUserCards(new BankUserCardGetAllRequest(userId));
        return Response.SUCCESS(response);
    }

    @GetMapping("/{userCardId}")
    public Response<BankUserCardGetResponse> getUserCard(@PathVariable Long userCardId) {
        BankUserCardGetResponse response = bankUserCardService.getUserCard(new BankUserCardGetRequest(userCardId));
        return Response.SUCCESS(response);
    }
}
