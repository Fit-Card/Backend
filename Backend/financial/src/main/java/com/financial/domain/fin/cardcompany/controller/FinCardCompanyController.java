package com.financial.domain.fin.cardcompany.controller;

import com.financial.domain.fin.cardcompany.model.dto.response.CardCompanyGetResponses;
import com.financial.domain.fin.cardcompany.service.FinCardCompanyService;
import com.financial.global.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/cards/companies")
@RequiredArgsConstructor
public class FinCardCompanyController {

    private final FinCardCompanyService finCardCompanyService;

    @PostMapping("/get/all")
    public Response<CardCompanyGetResponses> getCardCompanies() {
        CardCompanyGetResponses response = finCardCompanyService.getAllCardCompanies();
        return Response.SUCCESS(response);
    }
}
