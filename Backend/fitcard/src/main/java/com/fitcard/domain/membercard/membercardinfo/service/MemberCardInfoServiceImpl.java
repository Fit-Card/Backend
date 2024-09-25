package com.fitcard.domain.membercard.membercardinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.repository.MemberRepository;
import com.fitcard.domain.membercard.membercardinfo.exception.MemberCardGetAllRenewalException;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetAllRenewalResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetRenewalResponse;
import com.fitcard.domain.membercard.membercardinfo.repository.MemberCardInfoRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MemberCardInfoServiceImpl implements MemberCardInfoService {

    private final MemberCardInfoRepository memberCardInfoRepository;
    private final MemberRepository memberRepository;
    private final CardCompanyRepository cardCompanyRepository;
    private final CardInfoRepository cardInfoRepository;
    private final RestClient restClient;
    private final String MEMBER_CARD_INFO_GET_URI;

    public MemberCardInfoServiceImpl(MemberCardInfoRepository memberCardInfoRepository, MemberRepository memberRepository,
                                     CardCompanyRepository cardCompanyRepository, CardInfoRepository cardInfoRepository,
                                     @Value("${financial.user-card.get-all}")String memberCardInfoGetUri) {
        this.cardCompanyRepository = cardCompanyRepository;
        this.memberCardInfoRepository = memberCardInfoRepository;
        this.memberRepository = memberRepository;
        this.cardInfoRepository = cardInfoRepository;
        this.restClient = RestClient.create();
        this.MEMBER_CARD_INFO_GET_URI = memberCardInfoGetUri;
    }

    @Override
    public MemberCardGetAllRenewalResponses getAllRenewalMemberCardsFromFinancial(int memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberCardGetAllRenewalException(ErrorCode.MEMBER_NOT_FOUND, "해당하는 사용자가 없습니다"));
        if (member.getUserSeqNo().isEmpty()) {
            throw new MemberCardGetAllRenewalException(ErrorCode.NOT_FOUND_FINANCIAL_USER_SEQ, "본인 인증 후 마이데이터 연결이 필요합니다.");
        }

        String response = restClient.get()
                .uri(MEMBER_CARD_INFO_GET_URI+"/"+member.getUserSeqNo())
                .retrieve()
                .body(String.class);

        return parsingGetAllRenewalMemberCardsFromJson(response);
    }

    private MemberCardGetAllRenewalResponses parsingGetAllRenewalMemberCardsFromJson(String response){
        ObjectMapper objectMapper = new ObjectMapper();

        List<MemberCardGetRenewalResponse> memberCardGetRenewalResponses = new ArrayList<>();

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");
            JsonNode bankUserCardGetResponses = dataNode.get("bankUserCardGetResponses");


            for (JsonNode bankUserCardGetResponse : bankUserCardGetResponses) {
                Long financialUserCardId = bankUserCardGetResponse.get("bankUserCardId").asLong();
                String bankCardId = bankUserCardGetResponse.get("bankCardId").asText();
                String cardCompanyId = bankUserCardGetResponse.get("finCardCompanyId").asText();

                if(!cardCompanyRepository.existsByFinancialCardId(cardCompanyId)) continue;

                Optional<CardInfo> optionalCardInfo = cardInfoRepository.findByFinancialCardId(bankCardId);
                Optional<CardCompany> optionalCardCompany = cardCompanyRepository.findByFinancialCardId(cardCompanyId);
                if (optionalCardCompany.isEmpty() || optionalCardInfo.isEmpty()) {
                    continue;
                }
                CardCompany cardCompany = optionalCardCompany.get();
                CardInfo cardInfo = optionalCardInfo.get();

                String expiredDate = bankUserCardGetResponse.get("expiredDate").asText();
                if(memberCardInfoRepository.existsByFinancialCardId(bankCardId)){
                    continue;
                }
                MemberCardGetRenewalResponse memberCardGetRenewalResponse = MemberCardGetRenewalResponse.of(cardInfo, cardCompany, expiredDate, financialUserCardId);
                memberCardGetRenewalResponses.add(memberCardGetRenewalResponse);

            }
        } catch (Exception e) {
            log.error("json 파싱 실패!! {}", e.getMessage());
            log.error("{}", e.getStackTrace());
            throw new MemberCardGetAllRenewalException(ErrorCode.INTERNAL_SERVER_ERROR, "JSON 변환에 실패했습니다.");
        }
        return MemberCardGetAllRenewalResponses.from(memberCardGetRenewalResponses);
    }
}
