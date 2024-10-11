package com.financial.domain.fin.cardcompany.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardCompanyGetResponses {

    private List<CardCompanyGetResponse> cardCompanies;

    private int size;

    public static CardCompanyGetResponses from(List<CardCompanyGetResponse> cardCompanyGetResponses) {
        return new CardCompanyGetResponses(cardCompanyGetResponses, cardCompanyGetResponses.size());
    }
}
