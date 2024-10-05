package com.financial.domain.bank.cardevent.controller;

import com.financial.domain.bank.cardbenefit.model.dto.request.BankCardBenefitAddRequest;
import com.financial.domain.bank.cardbenefit.service.BankCardBenefitService;
import com.financial.domain.bank.cardevent.service.BankCardEventService;
import com.financial.global.config.swagger.SwaggerApiSuccess;
import com.financial.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "카드사 카드 혜택 관련 API")
@RestController
@Slf4j
@RequestMapping("/bank/cards/events")
@RequiredArgsConstructor
public class BankCardEventController {

    private final BankCardEventService bankCardEventService;

    @PostMapping("/post/{bankCardId}")
    public Response<?> createCardEvent(@PathVariable String bankCardId){
        System.out.println(bankCardId);
        bankCardEventService.createBankCardEvent(bankCardId);
        return Response.SUCCESS("kb카드 이벤트 저장완료");
    }

}
