package com.fitcard.domain.merchant.merchantinfo.controller;


import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantInfoSaveAllRequest;
import com.fitcard.domain.merchant.merchantinfo.model.dto.response.MerchantGetResponse;
import com.fitcard.domain.merchant.merchantinfo.service.MerchantInfoService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "가맹점 정보 API")
@RestController
@RequestMapping("/merchant/info")
@RequiredArgsConstructor
public class MerchantInfoController {
    private final MerchantInfoService merchantInfoService;

    @Operation(summary = "가맹점 상세 조회 API", description = "가맹점 ID를 통해 가맹점 정보를 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 상세 조회")
    @PostMapping("/get/{merchantId}")
    public Response<MerchantGetResponse> getMerchantName(@PathVariable Integer merchantId) {
        MerchantGetResponse response = merchantInfoService.getMerchant(merchantId);
        return Response.SUCCESS(response, "가맹점 이름 조회에 성공했습니다.");
    }

    @Operation(hidden = true)
    @PostMapping("/post/")
    public Response<?> createMerchants(@Valid @RequestBody MerchantInfoSaveAllRequest request) {
        log.info("request: {} {}", request.getMerchantNames(), request.getCategory());
        merchantInfoService.createAll(request);
        return Response.SUCCESS();
    }

}
