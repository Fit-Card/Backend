package com.fitcard.domain.member.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.member.exception.IncorrectPasswordException;
import com.fitcard.domain.member.exception.MemberGetUserSeqNoFromFinancialException;
import com.fitcard.domain.member.exception.MemberNotFoundException;
import com.fitcard.domain.member.exception.MemberUpdateFailedException;
import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.model.dto.request.MemberSaveFcmTokenRequest;
import com.fitcard.domain.member.model.dto.request.MemberSendJoinFirebaseRequest;
import com.fitcard.domain.member.model.dto.request.MemberUpdatePhoneRequest;
import com.fitcard.domain.member.model.dto.request.MemberUpdateRequest;
import com.fitcard.domain.member.model.dto.response.MemberGetResponse;
import com.fitcard.domain.member.model.dto.response.MemberUpdateResponse;
import com.fitcard.domain.member.repository.MemberRepository;
import com.fitcard.domain.member.repository.SmsCertificationDao;
import com.fitcard.domain.membercard.membercardinfo.model.dto.response.MemberCardGetRenewalResponse;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.firebase.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseService firebaseService;
    private final String USER_SEQ_NO_GET_URI;
    private final RestClient restClient;
    private final SmsCertificationDao smsCertificationDao;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, FirebaseService firebaseService,
                             SmsCertificationDao smsCertificationDao,
                             @Value("${financial.user.get-userseqno}") String userSeqNoUri) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.firebaseService = firebaseService;
        this.USER_SEQ_NO_GET_URI = userSeqNoUri;
        this.restClient = RestClient.create();
        this.smsCertificationDao = smsCertificationDao;
    }

    @Override
    public MemberGetResponse getUser(String loginId) {
        // 로그인한 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 사용자 정보 반환
        return MemberGetResponse.of(member);
    }

    @Override
    public MemberUpdateResponse updateUser(String loginId, MemberUpdateRequest request) {
        // 로그인한 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IncorrectPasswordException(ErrorCode.INCORRECT_PASSWORD, "비밀번호가 일치하지 않습니다.");
        }

        // 사용자 비밀번호 암호화 후 업데이트
        String encryptedPassword = passwordEncoder.encode(request.getNewPassword());

        // 사용자 정보 수정
        try {
            member.update(encryptedPassword);
            memberRepository.save(member);
        } catch (Exception e) {
            throw new MemberUpdateFailedException(ErrorCode.MEMBER_UPDATE_FAILED, "사용자 정보 수정에 실패했습니다.");
        }

        // 수정된 사용자 정보 반환
        return MemberUpdateResponse.from(member);
    }

    @Override
    public MemberUpdateResponse updatePhoneNumber(String loginId, MemberUpdatePhoneRequest request) {
        // 로그인한 사용자 정보를 DB에서 조회
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 사용자 전화번호 수정
        member.updatePhoneNumber(request.getNewPhoneNumber());

        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new MemberUpdateFailedException(ErrorCode.MEMBER_UPDATE_FAILED, "사용자 정보 수정에 실패했습니다.");
        }

        // 수정된 사용자 정보 반환
        return MemberUpdateResponse.from(member);
    }


    @Override
    public void createFcmToken(MemberSaveFcmTokenRequest request, Integer memberId) {
        //todo: 예외처리 필요
        firebaseService.sendJoinRequest(MemberSendJoinFirebaseRequest.of(request, memberId));
    }

    @Override
    public void updateMemberUserSeqNoFromFinancial(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberGetUserSeqNoFromFinancialException(ErrorCode.MEMBER_NOT_FOUND));
        String userSeqNo = sendGetUserSeqNoRequestToFinancial(member);
        member.updateUserSeqNo(userSeqNo);
    }

    private String sendGetUserSeqNoRequestToFinancial(Member member) {
        String verifyCode = smsCertificationDao.getFinCertification(member.getPhoneNumber().replace("-",""));
        String requestUri = USER_SEQ_NO_GET_URI+"phone="+member.getPhoneNumber().replace("-","")+"&verificationCode="+verifyCode;
        log.info("requestUri: {}", requestUri);
        String response = restClient.get()
                .uri(requestUri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new MemberGetUserSeqNoFromFinancialException(ErrorCode.UNVERIFIED_PHONE_NUMBER, "인증되지 않은 user입니다");
                })
                .body(String.class);
        return parsingGetUserSeqNoResponse(response);
    }

    private String parsingGetUserSeqNoResponse(String response){
        ObjectMapper objectMapper = new ObjectMapper();

        List<MemberCardGetRenewalResponse> memberCardGetRenewalResponses = new ArrayList<>();
        String finUserId = "";
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");
            finUserId = dataNode.get("finUserId").asText();
        } catch (Exception e) {
            log.error("json 파싱 실패!! {}", e.getMessage());
            e.printStackTrace();
            throw new MemberGetUserSeqNoFromFinancialException(ErrorCode.JSON_PARSING_ERROR, "JSON 변환에 실패했습니다.");
        }
        return finUserId;
    }


}
