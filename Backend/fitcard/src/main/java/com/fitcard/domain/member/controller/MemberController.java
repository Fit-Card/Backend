package com.fitcard.domain.member.controller;


import com.fitcard.domain.member.model.dto.request.MemberUpdateRequest;
import com.fitcard.domain.member.model.dto.response.MemberGetResponse;
import com.fitcard.domain.member.model.dto.response.MemberUpdateResponse;
import com.fitcard.domain.member.service.MemberService;
import com.fitcard.global.config.SecurityUtil;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Tag(name = "회원 관련 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "사용자 정보 조회 API", description = "로그인한 사용자의 정보를 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 정보 조회에 성공했습니다.")
    @PostMapping("/get")
    public Mono<Response<MemberGetResponse>> getUser() {
        return SecurityUtil.getLoginId()
                .flatMap(loginId -> {
                    MemberGetResponse response = memberService.getUser(loginId);
                    return Mono.just(Response.SUCCESS(response, "사용자 정보 조회에 성공했습니다."));
                });
    }

    @Operation(summary = "사용자 정보 수정 API", description = "사용자의 정보를 수정합니다.")
    @SwaggerApiSuccess(description = "사용자 정보 수정에 성공했습니다.")
    @PostMapping("/update")
    public Mono<Response<MemberUpdateResponse>> updateUser(@RequestBody MemberUpdateRequest request) {
        return SecurityUtil.getLoginId()
                .flatMap(loginId -> {
                    MemberUpdateResponse response = memberService.updateUser(loginId, request);
                    return Mono.just(Response.SUCCESS(response, "사용자 정보 수정에 성공헀습니다."));
                });
    }
}
