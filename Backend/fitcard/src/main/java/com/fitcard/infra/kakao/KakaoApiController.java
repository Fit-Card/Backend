package com.fitcard.infra.kakao;

import com.fitcard.domain.merchant.branch.service.BranchService;
import com.fitcard.global.response.Response;
import com.fitcard.infra.kakao.model.KakaoLocalWithCategoryFromGridInfoRequest;
import com.fitcard.infra.kakao.model.LocalInfo;
import com.fitcard.infra.kakao.service.KakaoLocalService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoApiController {

    private final KakaoLocalService kakaoLocalService;
    private final BranchService branchService;

    @Operation(hidden = true)
    @PostMapping("/post/local")
    public Response<?> getGridLocal(@RequestBody KakaoLocalWithCategoryFromGridInfoRequest request) {
        List<LocalInfo> localInfos = kakaoLocalService.getLocalWithCategoryInGridUsingRect(request);
        int savedBranchesNum = branchService.saveBranches(localInfos);

        return Response.SUCCESS("size: "+savedBranchesNum);
    }

}
