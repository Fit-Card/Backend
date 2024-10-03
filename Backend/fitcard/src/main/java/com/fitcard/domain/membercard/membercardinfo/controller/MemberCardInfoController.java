package com.fitcard.domain.membercard.membercardinfo.controller;

import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardCreateRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardDeleteRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardGetAllRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetAllRenewalResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetByAgeSpecificResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardPerformanceAndBenefitResponses;
import com.fitcard.domain.membercard.membercardinfo.service.MemberCardInfoService;
import com.fitcard.global.config.swagger.SwaggerApiSuccess;
import com.fitcard.global.guard.Login;
import com.fitcard.global.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "사용자 카드 관련 API")
@RestController
@Slf4j
@RequestMapping("/members/cards")
@RequiredArgsConstructor
public class MemberCardInfoController {

    private final MemberCardInfoService memberCardInfoService;

    @Operation(summary = "사용자 카드 전체 조회 API", description = "사용자의 카드 전체 목록을 조회합니다. 기본 정렬은 사전순입니다.")
    @SwaggerApiSuccess(description = "사용자 카드 전체 조회를 성공했습니다.")
    @PostMapping("/get/all")
    public Response<MemberCardGetResponses> getMemberCardAll(@Login Integer memberId) {
        MemberCardGetResponses response = memberCardInfoService.getAllMemberCards(new MemberCardGetAllRequest(memberId));
        return Response.SUCCESS(response);
    }

    @Operation(summary = "사용자 카드 생성 API", description = "사용자의 카드를 생성합니다. 한 번에 여러개의 카드를 생성할 수 있습니다.")
    @SwaggerApiSuccess(description = "사용자 카드 전체 생성을 성공했습니다.")
    @PostMapping("/post")
    public Response<?> createMemberCards(@Login Integer memberId,
                                         @RequestBody MemberCardCreateRequest request) {
        memberCardInfoService.createMemberCards(request, memberId);
        return Response.SUCCESS();
    }

    @Operation(summary = "사용자 카드 삭제 API", description = "사용자의 카드를 삭제합니다.")
    @SwaggerApiSuccess(description = "사용자 카드 삭제를 성공했습니다.")
    @PostMapping("/delete")
    public Response<?> deleteMemberCards(@Login Integer memberId,
                                         @RequestBody MemberCardDeleteRequest request) {
        memberCardInfoService.deleteMemberCard(request);
        return Response.SUCCESS();
    }

    @Operation(summary = "사용자 카드 갱신 정보 조회 API", description = "사용자의 카드를 추가하기 위해 갱신한 카드 정보를 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 카드 갱신 정보 조회를 성공했습니다.")
    @PostMapping("/get/renewal")
    public Response<MemberCardGetAllRenewalResponses> getRenewalMemberCards(@Login Integer memberId) {
        MemberCardGetAllRenewalResponses response = memberCardInfoService.getAllRenewalMemberCardsFromFinancial(memberId);
        return Response.SUCCESS(response, "사용자 카드 갱신 정보 조회를 성공했습니다.");
    }

    @Operation(summary = "사용자 나이대 카드 사용순 조회 API", description = "사용자의 나이대에 해당하는 카드를 사용 순으로 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 나이대 카드 사용순 조회를 성공했습니다.")
    @PostMapping("/get/age-specific/{size}")
    public Response<MemberCardGetByAgeSpecificResponses> getMemberCardsByAgeSpecific(@Login Integer memberId,
                                                                                     @PathVariable("size") int size) {
        MemberCardGetByAgeSpecificResponses response = memberCardInfoService.getMemberCardsByAgeSpecific(memberId, size);
        return Response.SUCCESS(response);
    }

    @Operation(summary = "사용자 카드 실적 현황과 랜덤 혜택 조회 API", description = "사용자 카드 실적 현황과 랜덤 혜택을 조회합니다.")
    @SwaggerApiSuccess(description = "사용자 카드 실적 현황과 랜덤 혜택 조회를 성공했습니다.")
    @PostMapping("/get/performance-and-benefit")
    public Response<MemberCardPerformanceAndBenefitResponses> getMemberCardPaymentStatusAndBenefits(@Login Integer memberId) {
        MemberCardPerformanceAndBenefitResponses response = memberCardInfoService.getMemberCardPerformanceAndBenefits(memberId);
        return Response.SUCCESS(response);
    }
}
