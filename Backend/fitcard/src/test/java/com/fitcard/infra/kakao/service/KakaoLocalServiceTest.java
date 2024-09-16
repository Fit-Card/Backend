package com.fitcard.infra.kakao.service;

import com.fitcard.infra.kakao.model.KakaoCategoryRectRequestQueryParameter;
import com.fitcard.infra.kakao.model.KakaoCategoryRequestQueryParameter;
import com.fitcard.infra.kakao.model.KakaoLocalWithCategoryFromGridInfoRequest;
import com.fitcard.infra.kakao.model.LocalInfo;
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
                50);

        List<LocalInfo> localWithCategoryFromKakao = kakaoLocalService.getLocalWithCategoryFromKakao(queryParameter);

        log.info("size: {}",localWithCategoryFromKakao.size());
        Assertions.assertThat(localWithCategoryFromKakao).isNotEmpty();
    }

    @Test
    @DisplayName("kakao local category API를 이용해 rect 범위 안의 location 정보를 불러옵니다.")
    public void getLocalWithCategoryAndRectFromKakao() {
        KakaoCategoryRectRequestQueryParameter queryParameter = new KakaoCategoryRectRequestQueryParameter("FD6",
                "126.546797053228",
                "37.311004215224",
                "127.181509882205",
                "37.311004215224");

        List<LocalInfo> localWithCategoryFromKakao = kakaoLocalService.getLocalWithCategoryAndRectFromKakao(queryParameter);

        log.info("size: {}",localWithCategoryFromKakao.size());
        Assertions.assertThat(localWithCategoryFromKakao).isNotEmpty();
    }

    @Test
    @DisplayName("범위에 대한 location 정보를 불러옵니다.")
    public void getLocalWithCategoryFromGrid() {

        KakaoLocalWithCategoryFromGridInfoRequest request = new KakaoLocalWithCategoryFromGridInfoRequest(
                "FD6",
                50,
                126.732055331217,
                37.5079080178341,
                126.741038736625,
                37.5191692206235
        );

        List<LocalInfo> localWithCategoryFromGrid = kakaoLocalService.getLocalWithCategoryFromGrid(request);
        log.info("size: {}",localWithCategoryFromGrid.size());
        Assertions.assertThat(localWithCategoryFromGrid).isNotEmpty();


    }

}