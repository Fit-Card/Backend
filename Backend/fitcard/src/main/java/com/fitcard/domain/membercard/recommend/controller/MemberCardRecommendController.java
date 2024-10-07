package com.fitcard.domain.membercard.recommend.controller;

import com.fitcard.domain.membercard.recommend.service.MemberCardRecommendService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 카드 추천 관련 API")
@RestController
@Slf4j
@RequestMapping("/members/cards/recommends")
@RequiredArgsConstructor
public class MemberCardRecommendController {

    private final MemberCardRecommendService memberCardRecommendService;

    @Operation(hidden = true, summary = "사용자 추천 카드 생성 API", description = "지난달의 사용자 카드 이용내역을 바탕으로 추천 카드를 생성합니다.")
    @SwaggerApiSuccess(description = "사용자 추천 카드 생성을 성공했습니다.")
    @PostMapping("/post")
    public Response<?> createMemberCardRecommend() {
        memberCardRecommendService.createMemberCardRecommend();
        return Response.SUCCESS();
    }
}
