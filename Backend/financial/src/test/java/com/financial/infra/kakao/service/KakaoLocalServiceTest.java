package com.financial.infra.kakao.service;

import com.financial.infra.kakao.model.KakaoCategoryRequestQueryParameter;
import com.financial.infra.kakao.model.LocalInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KakaoLocalServiceTest {
    @Autowired
    KakaoLocalService kakaoLocalService;

    @Value("${webclient.kakao.origin}")
    String originUri;

    @Value("${webclient.kakao.local.category}")
    String categoryUri;

    @Value("${webclient.kakao.apikey}")
    String kakoApiKey;

    @Test
    @DisplayName("kakao local category API를 이용해 x,y 좌표 반경 radius 안의 location 정보를 불러옵니다.")
    public void getLocalWithCategoryFromKakao() {
        KakaoCategoryRequestQueryParameter queryParameter = new KakaoCategoryRequestQueryParameter("FD6",
                "127.05897078335246",
                "37.506051888130386",
                20000,
                1);

        List<LocalInfo> localWithCategoryFromKakao = kakaoLocalService.getLocalWithCategoryFromKakao(queryParameter);

        localWithCategoryFromKakao.forEach(l -> {
            log.info("response: {}",l.toString());
        });
        Assertions.assertThat(localWithCategoryFromKakao).isNotEmpty();
    }

}