package com.fitcard.domain.merchant.merchantinfo.service;

import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantInfoSaveAllRequest;
import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantSearchRequest;
import com.fitcard.domain.merchant.merchantinfo.model.dto.response.MerchantGetResponse;
import com.fitcard.domain.merchant.merchantinfo.model.dto.response.MerchantSearchResponse;
import com.fitcard.domain.merchant.merchantinfo.model.dto.response.MerchantSearchResponses;

import java.util.List;

public interface MerchantInfoService {

    //가맹점 list 저장
    void createAll(MerchantInfoSaveAllRequest request);
    MerchantGetResponse getMerchant(Integer merchantId);
    List<MerchantSearchResponse> getMerchantByMerchantNameKeyword(MerchantSearchRequest request);
}
