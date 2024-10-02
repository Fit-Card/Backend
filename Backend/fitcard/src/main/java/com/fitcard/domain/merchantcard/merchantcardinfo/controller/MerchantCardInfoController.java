package com.fitcard.domain.merchantcard.merchantcardinfo.controller;

import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardBankResponse;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardBankResponses;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponse;
import com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response.MerchantCardResponses;
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
    @PostMapping("/get/merchantcard/{merchantId}")
    public Response<MerchantCardResponses> getMerchantCard(@PathVariable Integer merchantId) {
        List<MerchantCardResponse> merchantCardInfoList = merchantCardInfoService.getMerchantCardInfo(merchantId);
        return Response.SUCCESS(MerchantCardResponses.from(merchantCardInfoList), "가맹점 혜택 조회에 성공했습니다.");
    }

    @Operation(summary = "가맹점-카드 전체 조회 API", description = "가맹점에 해당되는 카드 전체 리스트를 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 혜택 조회에 성공했습니다.")
    @PostMapping("/get/all")
    public Response<MerchantCardResponses> getAllMerchantCard() {
        List<MerchantCardResponse> merchantCardInfoList = merchantCardInfoService.getMerchantCards();
        return Response.SUCCESS(MerchantCardResponses.from(merchantCardInfoList), "가맹점 혜택 전체 조회에 성공했습니다.");
    }

    @Operation(summary = "가맹점-카드 은행 조회 API", description = "가맹점 혜택에 해당되는 카드 은행사 리스트를 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 혜택 은행사 리스트 조회에 성공했습니다.")
    @PostMapping("/get/banks/{merchantId}")
    public Response<MerchantCardBankResponses> getMerchantCardBank(@PathVariable Integer merchantId) {
        List<MerchantCardBankResponse> responses = merchantCardInfoService.getMerchantCardBank(merchantId);
        return Response.SUCCESS(MerchantCardBankResponses.from(responses), "가맹점 혜택 카드 은행사 리스트 조회 성공했습니다.");
    }

    @Operation(summary = "가맹점-카드 전체 조회 API", description = "가맹점 혜택에 해당되는 카드 전체 리스트를 조회합니다.")
    @SwaggerApiSuccess(description = "가맹점 혜택 조회에 성공했습니다.")
    @PostMapping("/get/benefits/{merchantId}")
    public Response<?> getMerchantCardDetail(@PathVariable Integer merchantId) {
        List<MerchantCardResponse> merchantCardInfoList = merchantCardInfoService.getMerchantCards();
        return Response.SUCCESS(null, "가맹점 혜택 전체 조회에 성공했습니다.");
    }

    @PostMapping("/post")
    public Response<?> createAll(){
        merchantCardInfoService.createAll();
        return Response.SUCCESS("저장 완료");
    }


}