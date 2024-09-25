package com.fitcard.domain.merchant.merchantinfo.service;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantInfoSaveAllRequest;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.global.util.StringArrayListConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MerchantInfoServiceImpl implements MerchantInfoService {

    private final MerchantInfoRepository merchantInfoRepository;

    @Override
    public void createAll(MerchantInfoSaveAllRequest request) {
        List<String> merchantNames = StringArrayListConverter.convertStringToList(request.getMerchantNames());
        //todo: category를 string으로 저장하지 않고 categoryNum(int)로 저장하게 변경할지 의논 필요
        List<MerchantInfo> merchantInfos = merchantNames.stream()
                .map(m -> MerchantInfo.of(m, request.getCategory(), ""))
                .toList();
        merchantInfoRepository.saveAll(merchantInfos);
    }

    @Override
    public MerchantInfo getMerchantByName(String merchantName) {
        return merchantInfoRepository.findByName(merchantName).orElseThrow(() -> new RuntimeException("에러"));
    }
}
