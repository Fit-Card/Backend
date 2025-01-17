package com.fitcard.domain.merchantcard.merchantcardinfo.service;

import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.request.MerchantCardBankRequest;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.request.MerchantCardBenefitRequest;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardBankResponse;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardBenefitResponse;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponse;

import java.util.List;

public interface MerchantCardInfoService {
    List<MerchantCardResponse> getMerchantCards();
    List<MerchantCardResponse> getMerchantCardInfo(Integer merchantId);
    void createAll();
    List<MerchantCardBankResponse> getMerchantCardBank(Integer loginId, MerchantCardBankRequest request);
    List<MerchantCardBenefitResponse> getMerchantCardBenefit(Integer loginId,MerchantCardBenefitRequest merchantCardBenefitRequest);
}
