package com.fitcard.domain.merchantcard.merchantcardinfo.service;

import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponse;

import java.util.List;

public interface MerchantCardInfoService {
    List<MerchantCardResponse> getMerchantCards();
    List<MerchantCardResponse> getMerchantCardInfo(Integer merchantId);
    void createAll();
}
