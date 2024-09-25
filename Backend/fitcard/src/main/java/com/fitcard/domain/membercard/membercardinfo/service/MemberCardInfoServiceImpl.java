package com.fitcard.domain.membercard.membercardinfo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.card.cardinfo.repository.CardInfoRepository;
import com.fitcard.domain.card.company.model.CardCompany;
import com.fitcard.domain.card.company.repository.CardCompanyRepository;
import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.card.version.repository.CardVersionRepository;
import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.repository.MemberRepository;
import com.fitcard.domain.membercard.membercardinfo.exception.MemberCardCreateMemberCardsException;
import com.fitcard.domain.membercard.membercardinfo.exception.MemberCardDeleteException;
import com.fitcard.domain.membercard.membercardinfo.exception.MemberCardGetAllRenewalException;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardCreateRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.request.MemberCardDeleteRequest;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetAllRenewalResponses;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetRenewalResponse;
import com.fitcard.domain.membercard.membercardinfo.repository.MemberCardInfoRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    private final CardVersionRepository cardVersionRepository;
    private final RestClient restClient;
    private final String ALL_MEMBER_CARD_INFO_GET_URI;
    private final String MEMBER_CARD_INFO_GET_URI;

    public MemberCardInfoServiceImpl(MemberCardInfoRepository memberCardInfoRepository, MemberRepository memberRepository,
                                     CardCompanyRepository cardCompanyRepository, CardInfoRepository cardInfoRepository,
                                     CardVersionRepository cardVersionRepository,
                                     @Value("${financial.user-card.get-all}")String allMemberCardInfoGetUri,
                                     @Value("${financial.user-card.get}")String memberCardInfoGetUri) {
        this.cardCompanyRepository = cardCompanyRepository;
        this.memberCardInfoRepository = memberCardInfoRepository;
        this.memberRepository = memberRepository;
        this.cardInfoRepository = cardInfoRepository;
        this.cardVersionRepository = cardVersionRepository;
        this.restClient = RestClient.create();
        this.ALL_MEMBER_CARD_INFO_GET_URI = allMemberCardInfoGetUri;
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
                .uri(ALL_MEMBER_CARD_INFO_GET_URI+"/"+member.getUserSeqNo())
                .retrieve()
                .body(String.class);

        return parsingGetAllRenewalMemberCardsFromJson(response);
    }

    @Override
    public void createMemberCards(MemberCardCreateRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberCardCreateMemberCardsException(ErrorCode.MEMBER_NOT_FOUND, "해당하는 사용자가 없습니다."));
        List<MemberCardInfo> memberCardInfos = new ArrayList<>();
        request.getFinancialUserCardIds().forEach(financialUserCardId -> {
            memberCardInfos.add(getMemberCardInfoFromFinancial(financialUserCardId, member));
        });

        memberCardInfoRepository.saveAll(memberCardInfos);
    }

    @Override
    public void deleteMemberCard(MemberCardDeleteRequest request) {
        MemberCardInfo memberCardInfo = memberCardInfoRepository.findById(request.getMemberCardId())
                .orElseThrow(() -> new MemberCardDeleteException(ErrorCode.NOT_FOUND_MEMBER_CARD, "해당하는 사용자 카드가 없습니다."));
        memberCardInfoRepository.delete(memberCardInfo);
    }

    private MemberCardInfo getMemberCardInfoFromFinancial(long financialUserCardId, Member member) {
        String response = restClient.get()
                .uri(MEMBER_CARD_INFO_GET_URI+"/"+financialUserCardId)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new MemberCardCreateMemberCardsException(ErrorCode.NOT_FOUND_FINANCIAL_USER_CARD_ID, "financial에 해당하는 user card id가 없습니다");
                })
                .body(String.class);

        return parsingMemberCardInfoFromJson(response, member);
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
            throw new MemberCardGetAllRenewalException(ErrorCode.JSON_PARSING_ERROR, "JSON 변환에 실패했습니다.");
        }
        return MemberCardGetAllRenewalResponses.from(memberCardGetRenewalResponses);
    }

    private MemberCardInfo parsingMemberCardInfoFromJson(String response, Member member){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");

            log.info("data: {}", dataNode);
            long financialUserCardId = dataNode.get("bankUserCardId").asLong();
            String financialCardId = dataNode.get("bankCardId").asText();
//            String financialUserId = dataNode.get("finUserId").asText();
            String globalBrand = dataNode.get("globalBrand").asText();
            String expiredDateString = dataNode.get("expiredDate").asText();
            String cardMemberType = dataNode.get("cardMemberType").asText();
//            String financialCardCompanyId = dataNode.get("finCardCompanyId").asText();

            Optional<CardInfo> optionalCardInfo = cardInfoRepository.findByFinancialCardId(financialCardId);
            if (optionalCardInfo.isEmpty()) {
                return null;
            }
            CardInfo cardInfo = optionalCardInfo.get();
            
            //todo: cardversion 찾아야 함 - cardInfo와 expireDate-5년 한 date가 version의 생성일자보다 이후인 가장 작은 버전 찾기
            CardVersion cardVersion = cardVersionRepository.findById(1).orElseThrow(() -> new MemberCardCreateMemberCardsException(ErrorCode.NOT_FOUND_DATA, "card version 1번을 넣고 실행해야 합니다. test용"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
            YearMonth yearMonth = YearMonth.parse(expiredDateString, formatter);
            // YearMonth를 LocalDate로 변환 (첫 번째 날로 설정)
            LocalDate expiredDate = yearMonth.atDay(1);

            return MemberCardInfo.of(member, cardVersion, globalBrand, expiredDate, cardMemberType, financialUserCardId);
        } catch (Exception e){
            log.error("json 파싱 실패!! {}", e.getMessage());
            log.error("{}", e.getStackTrace());
            throw new MemberCardCreateMemberCardsException(ErrorCode.JSON_PARSING_ERROR, "JSON 변환에 실패했습니다.");
        }
    }
}
