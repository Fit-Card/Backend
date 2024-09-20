package com.fitcard.domain.member.service;

import com.fitcard.domain.member.model.dto.request.MemberUpdateRequest;
import com.fitcard.domain.member.model.dto.response.MemberGetResponse;
import com.fitcard.domain.member.model.dto.response.MemberUpdateResponse;

public interface MemberService {
    // 사용자 정보 조회
    MemberGetResponse getUser(String loginId);

    // 사용자 정보 수정
    MemberUpdateResponse updateUser(String loginId, MemberUpdateRequest request);
}
