package com.financial.domain.bank.card.controller;

import com.financial.infra.CardGorillaWebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank/card")
public class BankCardController {

    @Autowired
    private CardGorillaWebClientService cardGorillaWebClientService;

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

}
