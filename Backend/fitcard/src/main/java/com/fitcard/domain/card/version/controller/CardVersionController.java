package com.fitcard.domain.card.version.controller;

import com.fitcard.domain.card.version.service.CardVersionService;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카드 버전 관련 API")
@RestController
@Slf4j
@RequestMapping("/cards/version")
@RequiredArgsConstructor
public class CardVersionController {

    private final CardVersionService cardVersionService;

    @PostMapping("/import")
    public Response<Integer> createCardVersionsFromFinancial() {
        int savedCardsCount = cardVersionService.createCardVersionsFromFinancial();
        return Response.SUCCESS(savedCardsCount);
    }

}
