package com.fitcard.domain.membercard.membercardinfo.service;

import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardCreateRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardDeleteRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardGetAllRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetAllRenewalResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetByAgeSpecificResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetResponses;

public interface MemberCardInfoService {

    MemberCardGetAllRenewalResponses getAllRenewalMemberCardsFromFinancial(int memberId);

    void createMemberCards(MemberCardCreateRequest request, Integer memberId);

    void deleteMemberCard(MemberCardDeleteRequest request);

    MemberCardGetResponses getAllMemberCards(MemberCardGetAllRequest request);

    MemberCardGetByAgeSpecificResponses getMemberCardsByAgeSpecific(Integer memberId, int size);

}
