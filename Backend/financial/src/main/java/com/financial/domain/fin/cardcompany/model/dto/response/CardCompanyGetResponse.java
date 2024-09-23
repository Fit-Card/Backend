package com.financial.domain.fin.cardcompany.model.dto.response;

import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardCompanyGetResponse {

    private String companyId;

    private String companyName;

    private String companyImageUrl;

    public static CardCompanyGetResponse of(FinCardCompany cardCompany){
        return new CardCompanyGetResponse(cardCompany.getId(), cardCompany.getName(), cardCompany.getImage());
    }
}
