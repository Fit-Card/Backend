package com.fitcard.domain.membercard.membercardinfo.service;

import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardCreateRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardDeleteRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetAllRenewalResponses;

public interface MemberCardInfoService {

    MemberCardGetAllRenewalResponses getAllRenewalMemberCardsFromFinancial(int memberId);

    void createMemberCards(MemberCardCreateRequest request);

    void deleteMemberCard(MemberCardDeleteRequest request);

}
