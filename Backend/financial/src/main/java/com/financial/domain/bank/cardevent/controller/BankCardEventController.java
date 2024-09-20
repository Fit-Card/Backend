package com.financial.domain.bank.cardevent.controller;

import com.financial.domain.bank.cardbenefit.model.dto.request.BankCardBenefitAddRequest;
import com.financial.domain.bank.cardbenefit.service.BankCardBenefitService;
import com.financial.domain.bank.cardevent.service.BankCardEventService;
import com.financial.global.config.swagger.SwaggerApiSuccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "카드사 카드 혜택 관련 API")
@RestController
@Slf4j
@RequestMapping("/bank/cards/benefits")
@RequiredArgsConstructor
public class BankCardEventController {

    private final BankCardEventService bankCardEventService;


}
