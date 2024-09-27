package com.financial.domain.bank.cardbenefit.service;

import com.financial.domain.bank.cardbenefit.model.dto.request.BankCardBenefitAddRequest;
import com.financial.domain.bank.cardbenefit.model.dto.response.BankCardBenefitGetResponses;

import java.util.List;

public interface BankCardBenefitService {

    // 여러 개의 카드 혜택을 추가하는 메서드 선언
    void addCardBenefits(List<BankCardBenefitAddRequest> requests);

    BankCardBenefitGetResponses getAllBenefits();
}
