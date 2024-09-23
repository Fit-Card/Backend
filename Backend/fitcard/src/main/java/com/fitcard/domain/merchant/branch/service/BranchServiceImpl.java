package com.fitcard.domain.merchant.branch.service;

import com.fitcard.domain.merchant.branch.model.Branch;
import com.fitcard.domain.merchant.branch.repository.BranchRepository;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantCategory;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.infra.kakao.model.LocalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final MerchantInfoRepository merchantInfoRepository;

    @Override
    public int saveBranches(List<LocalInfo> localInfos) {

        log.info("localInfo size: {}", localInfos.size());
        List<Branch> branches = localInfos.stream()
                .filter(l->!branchRepository.existsByKakaoLocalId(l.getPlaceId()))
                .map(localInfo -> {
                    //merchant 이미 있는지 확인, 없다면 저장
//                    if()
                    String merchantName = makeMerchentName(localInfo.getPlaceName(), localInfo.getPlaceName().split(" "), localInfo.getCategoryGroupCode());

                    Optional<MerchantInfo> optionalMerchantInfo = merchantInfoRepository.findByName(merchantName);
                    MerchantInfo merchantInfo = optionalMerchantInfo.orElseGet(() -> merchantInfoRepository.save(MerchantInfo.of(merchantName, localInfo.getCategoryGroupCode(), localInfo.getPlaceUrl())));

//                    log.info("merchantInfo: {}", merchantInfo);
//                    log.info("kakaoId: {}", localInfo.getPlaceId());
                    //branch 만들기
                    return Branch.of(localInfo, merchantInfo);
                })
                // kakaoLocalId로 중복 제거
                .collect(Collectors.toMap(
                        Branch::getKakaoLocalId,  // kakaoLocalId를 키로 사용
                        branch -> branch,         // Branch를 값으로 사용
                        (existing, replacement) -> existing // 동일한 kakaoLocalId가 있으면 기존 값 유지
                ))
                .values()
                .stream()
                .toList();

        log.info("branches size: {}", branches.size());
        List<Branch> savedBranches = branchRepository.saveAll(branches);
        return savedBranches.size();
    }

    private String makeMerchentName(String locationName, String[] placeNameSplits, String categoryGroupCode) {
        String merchantName = makeMerchantNameWithoutWord(placeNameSplits, "점");

        // 최종 결과를 저장할 StringBuilder

//        log.info("merchantName: {}  locationName: {}", merchantName, locationName);
        if(merchantName.equals(locationName)) {
            if(categoryGroupCode.equals(MerchantCategory.OIL.getCategoryCode())) {
                merchantName = makeMerchantNameWithoutWord(placeNameSplits, "주유소");
//                log.info("merchantName: {}", merchantName);
            }
        }

        // 마지막 공백을 제거한 최종 결과
        return merchantName;
    }

    private String makeMerchantNameWithoutWord(String[] placeNameSplits, String word){
        StringBuilder resultStringBuilder = new StringBuilder();

        if(placeNameSplits.length == 1) {
            return placeNameSplits[0];
        }

        // 각 단어를 확인하면서 ~word(~점, ~주유소)으로 끝나지 않는 경우에만 합침
        //마지막 단어가 ~점, ~주유소로 끝나는 경우 제외
        int length = placeNameSplits.length;
        for(int i=0;i<length;i++) {
            if(i==length-1 && placeNameSplits[i].endsWith(word)) {
                continue;
            }
            resultStringBuilder.append(placeNameSplits[i]).append(" ");
        }

        String result = resultStringBuilder.toString().trim();

        if(result.isEmpty()) return placeNameSplits[0];

        return result;
    }
}
