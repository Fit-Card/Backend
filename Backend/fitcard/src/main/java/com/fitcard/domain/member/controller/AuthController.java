package com.fitcard.domain.member.controller;

import com.fitcard.domain.member.model.dto.request.MemberLoginRequest;
import com.fitcard.domain.member.model.dto.request.MemberRegisterRequest;
import com.fitcard.domain.member.model.dto.response.MemberCheckIdResponse;
import com.fitcard.domain.member.model.dto.response.MemberLoginResponse;
import com.fitcard.domain.member.model.dto.response.MemberRegisterResponse;
import com.fitcard.domain.member.service.AuthService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 관련 API")
@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "사용자 회원가입 API", description = "사용자가 회원가입을 진행합니다.")
    @SwaggerApiSuccess(description = "사용자 회원가입을 성공했습니다.")
    @PostMapping("/register")
    public Response<MemberRegisterResponse> registerUser(@RequestBody MemberRegisterRequest request) {
        MemberRegisterResponse response = authService.register(request);
        return Response.SUCCESS(response, "회원가입에 성공했습니다.");
    }

    @Operation(summary = "아이디 중복 확인 API", description = "사용자의 아이디 중복 여부를 확인합니다.")
    @SwaggerApiSuccess(description = "아이디 중복 확인을 성공했습니다.")
    @PostMapping("/checkid")
    public Response<MemberCheckIdResponse> checkDuplicatedId(@RequestParam String userId) {
        boolean isDuplicated = authService.checkDuplicatedId(userId);
        MemberCheckIdResponse response = MemberCheckIdResponse.of(isDuplicated);
        return Response.SUCCESS(response, "아이디 중복 확인을 성공했습니다.");
    }

    @Operation(summary = "사용자 로그인 API", description = "사용자가 아이디와 비밀번호로 로그인합니다.")
    @SwaggerApiSuccess(description = "로그인 성공 시 JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public Response<MemberLoginResponse> loginUser(@RequestBody MemberLoginRequest request) {
        MemberLoginResponse response = authService.login(request);
        return Response.SUCCESS(response, "로그인 성공");
    }
}
