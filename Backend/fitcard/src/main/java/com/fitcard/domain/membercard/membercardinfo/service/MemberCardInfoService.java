package com.fitcard.domain.membercard.membercardinfo.service;

import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardCreateRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetAllRenewalResponses;

public interface MemberCardInfoService {

    MemberCardGetAllRenewalResponses getAllRenewalMemberCardsFromFinancial(int memberId);

    void createMemberCards(MemberCardCreateRequest memberCardCreateRequest);

}
