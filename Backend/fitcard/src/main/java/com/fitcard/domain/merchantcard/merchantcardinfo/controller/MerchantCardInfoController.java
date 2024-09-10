package com.fitcard.domain.merchantcard.merchantcardinfo.controller;

import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.request.MerchantCardBenefitRequest;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardBenefitResponses;
import com.fitcard.domain.merchantcard.merchantcardinfo.service.MerchantCardInfoService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "가맹점 혜택 관련 API")
@RestController
@RequestMapping("/merchant/card/info")
@RequiredArgsConstructor
public class MerchantCardInfoController {

    private final MerchantCardInfoService merchantCardInfoService;

    @Operation(summary = "가맹점 혜택 조회 API", description = "가맹점 혜택에 해당되는 카드 리스트를 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 혜택 조회에 성공했습니다.")
    @PostMapping("/get/benefits")
    public Response<MerchantCardBenefitResponses> getMerchantCardBenefits(
            @RequestParam MerchantCardBenefitRequest request) {
        return Response.SUCCESS(null, "가맹점 혜택 조회에 성공했습니다.");
    }
}