package com.financial.domain.bank.card.controller;

import com.financial.domain.bank.card.model.dto.response.BankCardGetResponses;
import com.financial.domain.bank.card.repository.BankCardRepository;
import com.financial.domain.bank.card.service.BankCardService;
import com.financial.global.response.Response;
import com.financial.infra.CardGorillaWebClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank/card")
@RequiredArgsConstructor
public class BankCardController {

    private final CardGorillaWebClientService cardGorillaWebClientService;
    private final BankCardService bankCardService;

    @GetMapping("/fetch")
    public ResponseEntity<String> fetchCardData() {
        try {
            for (int cardId = 1; cardId <= 2753; cardId++) {
                cardGorillaWebClientService.fetchCardInfo(cardId);
            }
            return ResponseEntity.ok("카드 정보 조회가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("카드 정보를 가져오는 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/{cardCompanyId}")
    public Response<BankCardGetResponses> getCardsByCompany(@PathVariable String cardCompanyId) {
        BankCardGetResponses response = bankCardService.getCardsByCompany(cardCompanyId);
        return Response.SUCCESS(response);
    }

    @GetMapping("")
    public Response<BankCardGetResponses> getCards() {
        BankCardGetResponses response = bankCardService.getCards();
        return Response.SUCCESS(response);
    }

}
