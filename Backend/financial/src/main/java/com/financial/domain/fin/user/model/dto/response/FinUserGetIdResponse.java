package com.financial.domain.fin.user.model.dto.response;

import com.financial.domain.fin.cardcompany.model.FinCardCompany;
import com.financial.domain.fin.user.model.FinUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FinUserGetIdResponse {

    private String finUserId;

    public static FinUserGetIdResponse of(FinUser finUser){
        return new FinUserGetIdResponse(finUser.getId());
    }
}
