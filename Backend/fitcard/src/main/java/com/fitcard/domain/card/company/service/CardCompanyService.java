package com.fitcard.domain.card.company.service;

import com.fitcard.domain.card.company.model.dto.response.CardCompanyGetAllResponses;

public interface CardCompanyService {

    CardCompanyGetAllResponses getAllCardCompany();

    int createAllCardCompanies();
}
