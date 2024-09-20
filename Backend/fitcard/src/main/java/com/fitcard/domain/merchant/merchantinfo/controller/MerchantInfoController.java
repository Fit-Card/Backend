package com.fitcard.domain.merchant.merchantinfo.controller;


import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantGetNameRequest;
import com.fitcard.domain.merchant.merchantinfo.model.dto.request.MerchantInfoSaveAllRequest;
import com.fitcard.domain.merchant.merchantinfo.model.dto.response.MerchantGetNameResponse;
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

    @Operation(summary = "가맹점 이름 조회 API", description = "가맹점 ID를 통해 가맹점 이름을 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 이름 조회에 성공했습니다.")
    @PostMapping("/get/name")
    public Response<MerchantGetNameResponse> getMerchantName(@RequestParam MerchantGetNameRequest request) {
        return Response.SUCCESS(null, "가맹점 이름 조회에 성공했습니다.");
    }

    @Operation(hidden = true)
    @PostMapping("/post/")
    public Response<?> saveMerchants(@Valid @RequestBody MerchantInfoSaveAllRequest request) {
        log.info("request: {} {}", request.getMerchantNames(), request.getCategory());
        merchantInfoService.saveAll(request);
        return Response.SUCCESS();
    }

}
