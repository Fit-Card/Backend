package com.fitcard.domain.membercard.recommend.service;

import com.fitcard.domain.membercard.recommend.model.dto.response.MemberCardRecommendResponses;

public interface MemberCardRecommendService {

    public void createMemberCardRecommend();

    MemberCardRecommendResponses getMemberCardAllRecommend(Integer memberId);
}
