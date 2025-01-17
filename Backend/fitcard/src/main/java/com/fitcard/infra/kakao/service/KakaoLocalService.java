package com.fitcard.infra.kakao.service;

import com.fitcard.infra.kakao.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class KakaoLocalService {

    private final String CATEGORY_LOCAL_REQUEST_URI;
    private final String ORIGIN_URI;
    private final String KAKAO_API_KEY;
    private final RestClient restClient;


    public KakaoLocalService(@Value("${webclient.kakao.origin}") String originUri,
                             @Value("${webclient.kakao.local.category}") String categoryUri,
                             @Value("${webclient.kakao.apikey}") String kakoApiKey, RestClient.Builder restClientBuilder) {
        this.ORIGIN_URI = originUri;
        this.CATEGORY_LOCAL_REQUEST_URI = categoryUri;
        this.KAKAO_API_KEY = kakoApiKey;
        this.restClient = RestClient.create();
    }

    /**
     * 사각형 범위의 카테고리 장소를 모두 불러옵니다.
     * @param request
     * @return
     */
    public List<LocalInfo> getLocalWithCategoryInGridUsingRect(KakaoLocalWithCategoryFromGridInfoRequest request) {
        List<LocalInfo> allLocalInfos = new ArrayList<>();
        double moveDistanceLat = 0.00045*50; // 위도 500미터 이동
        double moveDistanceLon = 0.00056*50; // 경도 500미터 이동 (서울 기준)

        // 위도와 경도 범위 내에서 50m씩 이동하면서 API 호출
        for (double lat = request.getMinLat(); lat <= request.getMaxLat(); lat += moveDistanceLat) {
            for (double lon = request.getMinLon(); lon <= request.getMaxLon(); lon += moveDistanceLon) {

                String minLon = Double.toString(lon);
                String minLat = Double.toString(lat);
                String maxLon = Double.toString(lon + moveDistanceLon); // 오른쪽 위 경도
                String maxLat = Double.toString(lat + moveDistanceLat); // 오른쪽 위 위도

                // rect 파라미터에 들어가는 순서: minLon, minLat, maxLon, maxLat (경도, 위도 순서)
                KakaoCategoryRectRequestQueryParameter parameter = new KakaoCategoryRectRequestQueryParameter(
                        request.getCategoryGroupCode(), minLon, minLat, maxLon, maxLat
                );

                List<LocalInfo> localInfos = getLocalWithCategoryAndRectFromKakao(parameter);
//                log.info("현재 rect: {}, {}, {}, {}", minLon, minLat, maxLon, maxLat);
                log.info("size: {}", localInfos.size());

                allLocalInfos.addAll(localInfos);

                // 중복된 데이터 제거
//                allLocalInfos = allLocalInfos.stream()
//                        .distinct()
//                        .collect(Collectors.toList());
            }
        }

        return allLocalInfos;
    }

    /**
     * 카카오에서 사각형 범위의 장소 정보를 받아온다.
     * @param parameter
     * @return
     */
    public List<LocalInfo> getLocalWithCategoryAndRectFromKakao(KakaoCategoryRectRequestQueryParameter parameter){

        //1. request를 바탕으로 요청 url 만들기
        KakaoCategoryLocalApiResponses responses = getKakaoCategoryLocalApiResponses(parameter, 1);

        if(responses == null){
            return List.of();
        }

        List<LocalInfo> localInfos = new ArrayList<>();

        localInfos.addAll(responses.getDocuments().stream()
                .map(LocalInfo::from)
                .toList());

        int totalPageCount = responses.getMeta().getTotal_count();
//        log.info("totalPageCount: {}", totalPageCount);
        int totalPageNum = totalPageCount/15;
        if(totalPageCount % 15 != 0){
            totalPageNum++;
        }
        for(int i = 2; i <= totalPageNum; i++){
            responses = getKakaoCategoryLocalApiResponses(parameter, i);
//            log.info("response size: {}", responses.getDocuments().size());
            localInfos.addAll(responses.getDocuments().stream()
                    .map(LocalInfo::from)
                    .toList());
            if(responses.getMeta().is_end()) {
                break;
            }
        }

//        log.info("response meta: {}", responses.getMeta());
//        log.info("localInfos size: {}", localInfos.size());
        return localInfos;
//        log.info("localInfos: {}", localInfos);
//        log.info("localInfos distinct size: {}", localInfos.stream()
//                .distinct()
//                .toList().size());
//
//        log.info("localInfos distinct: {}", localInfos.stream()
//                .distinct()
//                .toList());
//        return localInfos.stream()
//                .distinct()
//                .toList();
    }



    //해당 위치 기준으로 20km 반경의 list 뽑기 50m
    public List<LocalInfo> getLocalWithCategoryFromKakao(KakaoCategoryRequestQueryParameter parameter){

        //1. request를 바탕으로 요청 url 만들기
        KakaoCategoryLocalApiResponses responses = getKakaoCategoryLocalApiResponses(parameter, 1);

        if(responses == null){
            return List.of();
        }

        List<LocalInfo> localInfos = new ArrayList<>();

        localInfos.addAll(responses.getDocuments().stream()
                .map(LocalInfo::from)
                .toList());

        int totalPageCount = responses.getMeta().getPageable_count();

        for(int i = 2; i <= totalPageCount; i++){
            responses = getKakaoCategoryLocalApiResponses(parameter, i);
            localInfos.addAll(responses.getDocuments().stream()
                    .map(LocalInfo::from)
                    .toList());
            if(responses.getMeta().is_end()) break;
        }

//        log.info("response meta: {}", responses.getMeta());
        return localInfos.stream()
                .distinct()
                .toList();
    }

    private KakaoCategoryLocalApiResponses getKakaoCategoryLocalApiResponses(KakaoCategoryRectRequestQueryParameter parameter, int page){
        String requestUrl = ORIGIN_URI+CATEGORY_LOCAL_REQUEST_URI+"?category_group_code="+parameter.getCategory_group_code()
                +"&rect="+parameter.getTopX()+","+parameter.getTopY()+","+parameter.getBottomX()+","+parameter.getBottomY()
                +"&page="+page+"&size=15";
//        log.info("requestUrl: {}", requestUrl);
        return getRequestToKakao(requestUrl);
    }

    private KakaoCategoryLocalApiResponses getKakaoCategoryLocalApiResponses(KakaoCategoryRequestQueryParameter parameter, int page){
        String requestUrl = ORIGIN_URI+CATEGORY_LOCAL_REQUEST_URI+"?category_group_code="+parameter.getCategory_group_code()
                +"&x="+parameter.getX()+"&y="+parameter.getY()
                +"&radius="+parameter.getRadius()+"&page="+page;

        return getRequestToKakao(requestUrl);
    }

    private KakaoCategoryLocalApiResponses getRequestToKakao(String requestUrl){

        try {
            return restClient.get()
                    .uri(requestUrl)
                    .header("Authorization", "KakaoAK " + KAKAO_API_KEY)
                    .retrieve()
                    .body(KakaoCategoryLocalApiResponses.class);
        } catch (Exception e) {
            // 예외 발생 시 KakaoCategoryLocalApiResponses.empty() 반환
            log.error("Error occurred during API call: {}", e.getMessage());
            return KakaoCategoryLocalApiResponses.empty();
        }


//        return webClient.get()
//                .uri(requestUrl)
//                .header("Authorization", "KakaoAK " + KAKAO_API_KEY)
//                .retrieve()
//                .onStatus(httpStatusCode -> httpStatusCode.isError(), clientResponse -> {
//                    // 에러 응답 본문을 return
//                    return clientResponse.bodyToMono(String.class)
//                            .flatMap(errorMessage -> {
////                                log.error("Kakao API error response: {}", errorMessage);
//                                return Mono.error(new RuntimeException("Kakao API Error: " + errorMessage));
//                            });
//                })
//                .bodyToMono(KakaoCategoryLocalApiResponses.class)
//                .doOnError(throwable -> {
//                    log.info("kakao api error: {}", throwable.getMessage());
//                })
//                .onErrorResume(throwable -> {
//                    // 에러 발생 시 빈 응답 반환
//                    log.warn("Error occurred during API call, returning empty response. Error: {}", throwable.getMessage());
//                    return Mono.just(KakaoCategoryLocalApiResponses.empty()); // 빈 응답
//                })
//                .block();
    }
}
