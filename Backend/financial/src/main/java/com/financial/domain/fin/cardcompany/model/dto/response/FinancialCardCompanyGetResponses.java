package com.financial.domain.fin.cardcompany.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FinancialCardCompanyGetResponses {

    private List<FinancialCardCompanyGetResponse> cardCompanies;

    private int size;

    public static FinancialCardCompanyGetResponses from(List<FinancialCardCompanyGetResponse> financialCardCompanyGetRespons) {
        return new FinancialCardCompanyGetResponses(financialCardCompanyGetRespons, financialCardCompanyGetRespons.size());
    }
}
