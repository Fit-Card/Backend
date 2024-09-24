package com.financial.domain.fin.cardcompany.model.dto.response;

import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FinancialCardCompanyGetResponse {

    private String companyId;

    private String companyName;

    private String companyImageUrl;

    public static FinancialCardCompanyGetResponse of(FinCardCompany cardCompany){
        return new FinancialCardCompanyGetResponse(cardCompany.getId(), cardCompany.getName(), cardCompany.getImage());
    }
}
