package com.fitcard.domain.merchant.branch.service;

import com.fitcard.domain.merchant.branch.model.Branch;
import com.fitcard.domain.merchant.branch.model.dto.request.BranchCategoryRequest;
import com.fitcard.domain.merchant.branch.model.dto.request.BranchSearchRequest;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchCategoryResponse;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchCategoryResponses;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchGetResponse;
import com.fitcard.domain.merchant.branch.model.dto.response.BranchSearchResponse;
import com.fitcard.domain.merchant.branch.repository.BranchRepository;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantCategory;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.infra.kakao.model.LocalInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.Arrays;
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
    public int createBranches(List<LocalInfo> localInfos) {

        log.info("localInfo size: {}", localInfos.size());
        List<Branch> branches = localInfos.stream()
                .filter(l->!branchRepository.existsByKakaoLocalId(l.getPlaceId()))
                .map(localInfo -> {
                    //merchant 이미 있는지 확인, 없다면 저장
                    //문화시설, 편의점이라면 카카오의 카테고리 사용
                    String merchantName;
                    if (localInfo.getCategoryGroupCode().equals(MerchantCategory.CULTURE.getCategoryCode()) ||
                    localInfo.getCategoryGroupCode().equals(MerchantCategory.CONVENIENCE_STORE.getCategoryCode())) {
                        String[] splits = localInfo.getCategoryName().split(">");
                        merchantName = splits[splits.length - 1].trim();
                    }
                    else{
                        merchantName = makeMerchentName(localInfo.getPlaceName(), localInfo.getPlaceName().split(" "), localInfo.getCategoryGroupCode());
                    }

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
    @Override
    public List<BranchGetResponse> getBranchesAll() {
        List<Branch> branches = branchRepository.findAll();
        return branches.stream()
                .map(BranchGetResponse::from)
                .toList();
    }

    @Override
    public BranchGetResponse getBranchById(final Long merchantBranchId) {
        Branch branch = branchRepository.findById(merchantBranchId).orElseThrow(() -> new NotFoundException("branch not found"));
        return BranchGetResponse.from(branch);
    }

    @Override
    public List<BranchGetResponse> getBranchesByMerchantKeyword(BranchSearchRequest request) {
        List<Branch> branches = branchRepository.findBranchesByMerchantNameKeyword(request.getMerchantNameKeyword());

        return branches.stream()
                .map(BranchGetResponse::from)
                .toList();
    }

    @Override
    public List<BranchSearchResponse> getBranchesByMerchantKeywordPagination(BranchSearchRequest request, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10);

        List<Object[]> results = branchRepository.findBranchesByMerchantNameAndLocation(
                request.getMerchantNameKeyword(),
                request.getLatitude(),
                request.getLongitude(),
                pageable
        );

        return results.stream()
                .map(result -> {
                    Branch branch = (Branch) result[0];  // 첫 번째 요소는 Branch 객체
                    Double distance = (Double) result[1];  // 두 번째 요소는 계산된 거리 값
                    return BranchSearchResponse.of(branch, distance);
                })
                .collect(Collectors.toList());
    }

    @Override
    public BranchCategoryResponses getBranchesByMerchantCategoryPagination(BranchCategoryRequest request, int pageNo) {
        Pageable pageable = PageRequest.of(0, 50); // 한 번에 최대 50개의 데이터만 가져옴

        Page<Object[]> results;

        if(request.getCategory().equals("ALL")) {
            System.out.println("카테고리 없음");
            System.out.println(request.getCategory());

            results = branchRepository.findMerchantsNoCategoryWithinRectangle(
                    request.getLeftLatitude(),
                    request.getLeftLongitude(),
                    request.getRightLatitude(),
                    request.getRightLongitude(),
                    pageable
            );


        }else{
            results = branchRepository.findMerchantsWithinRectangle(
                    request.getCategory(),
                    request.getLeftLatitude(),
                    request.getLeftLongitude(),
                    request.getRightLatitude(),
                    request.getRightLongitude(),
                    pageable);

        }

        // 최대 50개의 데이터를 가져온 후, 페이지네이션 적용 (10개씩 나눠서 보여줌)
        List<Object[]> limitedResults = results.getContent().stream().limit(50).collect(Collectors.toList());
        int start = (pageNo - 1) * 10;
        int end = Math.min((start + 10), limitedResults.size()); // 페이지당 10개씩 나눔

        List<BranchCategoryResponse> paginatedList = limitedResults.subList(start, end).stream()
                .map(result -> {
                    MerchantInfo merchantInfo = (MerchantInfo) result[0];
                    Branch branch = (Branch) result[1];
                    return BranchCategoryResponse.of(branch, merchantInfo);
                })
                .collect(Collectors.toList());
        int totalElements = limitedResults.size();
        int totalPages = (int) Math.ceil((double) totalElements / 10); // 10개씩 나

        return BranchCategoryResponses.of(paginatedList, pageNo, totalPages); // 페이지 결과 반환
    }
}
