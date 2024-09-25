package com.fitcard.domain.merchant.merchantinfo.service;

import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantInfoSaveAllRequest;

public interface MerchantInfoService {

    //가맹점 list 저장
    void createAll(MerchantInfoSaveAllRequest request);
}
