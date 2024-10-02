package com.fitcard.domain.merchantcard.merchantcardinfo.service;

import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.card.version.repository.CardVersionRepository;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.model.dto.response.MerchantSearchResponse;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.MerchantCardInfo;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponse;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponses;
import com.fitcard.domain.merchantcard.merchantcardinfo.repository.MerchantCardInfoRepository;
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
    private final MerchantCardInfoRepository merchantCardInfoRepository;
    private final CardVersionRepository cardVersionRepository;

    @Override
    public List<MerchantCardResponse> getMerchantCards() {
        List<MerchantCardInfo> list = merchantCardInfoRepository.findAll();
        return list.stream()
                .map(MerchantCardResponse::from)
                .toList();
    }

    @Override
    public List<MerchantCardResponse> getMerchantCardInfo(Integer merchantId) {
        MerchantInfo merchantInfo = merchantInfoRepository.findByMerchantId(Long.valueOf(merchantId));
        List<MerchantCardInfo> list = merchantCardInfoRepository.findAllByMerchantId(merchantInfo);
        return list.stream()
                .map(MerchantCardResponse::from)
                .toList();
    }

    @Override
    public void createAll() {
        List<Object[]> mappings = merchantInfoRepository.findMerchantCard();
        System.out.println(mappings.size());
        // 조회된 데이터를 매핑 테이블에 삽입
        for (Object[] mapping : mappings) {
            Long merchantIdValue = (Long) mapping[0];  // merchant_id는 Long 타입
            Integer cardVersionIdValue = (Integer) mapping[1];  // card_version_id는 Integer 타입
            // merchantId와 cardVersionId를 실제 엔티티로 조회
            MerchantInfo merchantInfo = merchantInfoRepository.findById(Math.toIntExact(merchantIdValue))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid merchant ID: " + merchantIdValue));
            CardVersion cardVersion = cardVersionRepository.findById(cardVersionIdValue)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid card version ID: " + cardVersionIdValue));

            MerchantCardInfo merchantCardInfo = MerchantCardInfo.of(merchantInfo, cardVersion);
            merchantCardInfoRepository.save(merchantCardInfo);  // 매핑 엔티티 저장
        }
    }

}
