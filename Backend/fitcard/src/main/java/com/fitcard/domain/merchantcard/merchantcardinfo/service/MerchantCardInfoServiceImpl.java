package com.fitcard.domain.merchantcard.merchantcardinfo.service;

import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MerchantCardInfoServiceImpl implements MerchantCardInfoService {

    private final MerchantInfoRepository merchantInfoRepository;

    @Override
    public List<MerchantCardResponse> merchantCards(Integer merchantId) {
//        List<MerchantCardBenefitDescriptionResponse> = merchantCardInfoRepository.findAllById(merchantId);
        return null;
    }

    @Override
    public List<MerchantCardResponse> getMerchantCardInfo(Integer merchantId) {
        List<Object[]> list = merchantInfoRepository.findMerchantWithCardVersion(merchantId);
        return list.stream()
                .map(result -> {
                    Long merchantIdValue = (Long) result[0];  // merchantId는 Integer로 반환
                    Integer cardVersionId = (Integer) result[1];    // cardVersionId도 Integer로 반환
                    return MerchantCardResponse.of(merchantIdValue, cardVersionId);
                })
                .collect(Collectors.toList());
    }

}
