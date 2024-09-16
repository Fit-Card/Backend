package com.fitcard.domain.merchant.branch.service;

import com.fitcard.domain.merchant.branch.model.Branch;
import com.fitcard.domain.merchant.branch.repository.BranchRepository;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.infra.kakao.model.LocalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final MerchantInfoRepository merchantInfoRepository;

    @Override
    public void saveBranches(List<LocalInfo> localInfos) {

        List<Branch> branches = localInfos.stream()
                .map(localInfo -> {
                    //merchant 이미 있는지 확인, 없다면 저장
                    String merchantName = makeMerchentName(localInfo.getPlaceName().split(" "));

                    Optional<MerchantInfo> optionalMerchantInfo = merchantInfoRepository.findByName(merchantName);
                    MerchantInfo merchantInfo;
                    if (optionalMerchantInfo.isEmpty()) {
                        merchantInfo = merchantInfoRepository.save(MerchantInfo.of(merchantName, localInfo.getCategoryGroupCode(), localInfo.getPlaceUrl()));
                    } else {
                        merchantInfo = optionalMerchantInfo.get();
                    }

                    //branch 만들기
                    return Branch.of(localInfo, merchantInfo);
                })
                .filter(l -> !branchRepository.existsByKakaoLocalId(l.getKakaoLocalId()))
                .toList();

        branchRepository.saveAll(branches);
    }

    private String makeMerchentName(String[] placeNameSplits) {
        // 최종 결과를 저장할 StringBuilder
        StringBuilder result = new StringBuilder();

        // 각 단어를 확인하면서 "~점"으로 끝나지 않는 경우에만 합침
        for (String word : placeNameSplits) {
            if (!word.endsWith("점")) {
                result.append(word).append(" ");
            }
        }

        // 마지막 공백을 제거한 최종 결과
        return result.toString().trim();
    }
}
