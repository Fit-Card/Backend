package com.financial.infra.kakao.service;

import com.financial.infra.kakao.model.KakaoCategoryLocalApiResponses;
import com.financial.infra.kakao.model.KakaoCategoryRequestQueryParameter;
import com.financial.infra.kakao.model.LocalInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class KakaoLocalService {

    private final WebClient webClient;
    private final String CATEGORY_LOCAL_REQUEST_URI;
    private final String ORIGIN_URI;
    private final String KAKAO_API_KEY;


    public KakaoLocalService(WebClient webClient,
                             @Value("${webclient.kakao.origin}") String originUri,
                             @Value("${webclient.kakao.local.category}") String categoryUri,
                             @Value("${webclient.kakao.apikey}") String kakoApiKey) {
        this.webClient = webClient;
        this.ORIGIN_URI = originUri;
        this.CATEGORY_LOCAL_REQUEST_URI = categoryUri;
        this.KAKAO_API_KEY = kakoApiKey;
    }


    public List<LocalInfo> getLocalWithCategoryFromKakao(KakaoCategoryRequestQueryParameter parameter){

        //1. request를 바탕으로 요청 url 만들기
//        String x = "127.05897078335246";
//        String y = "37.506051888130386";
        String radius = "20000";
        String requestUrl = ORIGIN_URI+CATEGORY_LOCAL_REQUEST_URI+"?category_group_code="+parameter.getCategory_group_code()
                +"&x="+parameter.getX()+"&y="+parameter.getY()
                +"&radius="+parameter.getRadius()+"&page="+parameter.getPage();

        log.info("requestUrl: {}", requestUrl);

        //2. webClient.get으로 요청해서 LocalInfo list 받기

        KakaoCategoryLocalApiResponses responses = webClient.get()
                .uri(requestUrl)
                .header("Authorization", "KakaoAK " + KAKAO_API_KEY)
                .retrieve()
                .bodyToMono(KakaoCategoryLocalApiResponses.class)
                .doOnError(throwable -> {
                    log.info("kakao api error: {} localizedMessage: {}", throwable.getMessage(), throwable.getLocalizedMessage());
                })
                .block();

        if (responses != null) {
            return responses.getDocuments().stream()
                    .map(LocalInfo::from)
                    .toList();
        }

        return List.of();
    }
}
