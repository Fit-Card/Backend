package com.fitcard.domain.membercard.payment.controller;

import com.fitcard.domain.membercard.payment.model.dto.response.MemberCardPaymentGetWithCategoryResponse;
import com.fitcard.domain.membercard.payment.service.PaymentService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.guard.Login;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사용자 카드 결제내역 관련 API")
@RestController
@Slf4j
@RequestMapping("/members/cards/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "사용자 카드 카테고리별 결제내역 조회 API", description = "지난달의 사용자 카드 카테고리별 결제내역 목록을 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 카드 전체 조회를 성공했습니다.")
    @PostMapping("/get/category")
    public Response<MemberCardPaymentGetWithCategoryResponse> getMemberCardPaymentWithCategory(@Login Integer memberId) {
        MemberCardPaymentGetWithCategoryResponse response = paymentService.getMemberCardPaymentWithCategory(memberId);
        return Response.SUCCESS(response);
    }
}
