package com.fitcard.domain.member.controller;

import com.fitcard.domain.member.model.dto.request.MemberUpdateRequest;
import com.fitcard.domain.member.model.dto.response.MemberGetResponse;
import com.fitcard.domain.member.model.dto.response.MemberUpdateResponse;
import com.fitcard.domain.member.service.MemberService;
import com.fitcard.global.config.auth.SecurityUtil;
import com.fitcard.global.config.swagger.SwaggerApiError;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 관련 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "사용자 정보 조회 API", description = "로그인한 사용자의 정보를 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 정보 조회에 성공했습니다.")
    @SwaggerApiError({ErrorCode.MEMBER_NOT_FOUND,ErrorCode.INVALID_TOKEN})
    @PostMapping("/get")
    public Response<MemberGetResponse> getUser() {
        // 로그인한 사용자의 ID를 가져옴
        String loginId = SecurityUtil.getLoginId();
        MemberGetResponse response = memberService.getUser(loginId);
        return Response.SUCCESS(response, "사용자 정보 조회에 성공했습니다.");
    }

    @Operation(summary = "사용자 정보 수정 API", description = "사용자의 정보를 수정합니다.")
    @SwaggerApiSuccess(description = "사용자 정보 수정에 성공했습니다.")
    @SwaggerApiError({ErrorCode.MEMBER_NOT_FOUND,ErrorCode.INVALID_TOKEN})
    @PostMapping("/update")
    public Response<MemberUpdateResponse> updateUser(@RequestBody MemberUpdateRequest request) {
        // 로그인한 사용자의 ID를 가져옴
        String loginId = SecurityUtil.getLoginId();
        MemberUpdateResponse response = memberService.updateUser(loginId, request);
        return Response.SUCCESS(response, "사용자 정보 수정에 성공헀습니다.");
    }
}
