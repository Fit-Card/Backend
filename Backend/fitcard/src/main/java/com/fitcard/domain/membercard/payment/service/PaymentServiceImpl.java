package com.fitcard.domain.membercard.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.membercard.membercardinfo.exception.MemberCardCreateMemberCardsException;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.membercardinfo.repository.MemberCardInfoRepository;
import com.fitcard.domain.membercard.payment.exception.MemberCardPaymentGetStatusException;
import com.fitcard.domain.membercard.payment.model.MemberCardPaymentInfos;
import com.fitcard.domain.membercard.payment.model.Payment;
import com.fitcard.domain.membercard.payment.model.dto.request.MemberCardPaymentGetStatusRequest;
import com.fitcard.domain.membercard.payment.model.dto.response.MemberCardPaymentGetStatusResponse;
import com.fitcard.domain.membercard.payment.repository.PaymentRepository;
import com.fitcard.domain.membercard.performance.model.MemberCardPerformance;
import com.fitcard.domain.membercard.performance.repository.MemberCardPerformanceRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberCardInfoRepository memberCardInfoRepository;
    private final MemberCardPerformanceRepository memberCardPerformanceRepository;
    private final CardPerformanceRepository cardPerformanceRepository;
    private final String GET_MEMBER_CARD_PAYMENT;
    private final RestClient restClient;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MemberCardInfoRepository memberCardInfoRepository,
                              MemberCardPerformanceRepository memberCardPerformanceRepository, CardPerformanceRepository cardPerformanceRepository,
                              @Value("${financial.user-card.payment.get-all}") String GET_MEMBER_CARD_PAYMENT) {
        this.paymentRepository = paymentRepository;
        this.memberCardInfoRepository = memberCardInfoRepository;
        this.memberCardPerformanceRepository = memberCardPerformanceRepository;
        this.cardPerformanceRepository = cardPerformanceRepository;
        this.GET_MEMBER_CARD_PAYMENT = GET_MEMBER_CARD_PAYMENT;
        this.restClient = RestClient.create();
    }

    @Override
    public MemberCardPaymentGetStatusResponse getMemberCardPaymentStatus(MemberCardPaymentGetStatusRequest request) {
        MemberCardInfo memberCardInfo = memberCardInfoRepository.findById(request.getMemberCardId())
                .orElseThrow(() -> new MemberCardPaymentGetStatusException(ErrorCode.NOT_FOUND_MEMBER_CARD, "사용자 카드가 없습니다."));

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        Optional<MemberCardPerformance> optionalMemberCardPerformance = memberCardPerformanceRepository.findByMemberCardAndYearAndMonth(memberCardInfo, year, month);
        MemberCardPerformance memberCardPerformance = optionalMemberCardPerformance
                .orElseGet(() -> memberCardPerformanceRepository.save(MemberCardPerformance.empty(year, month, memberCardInfo)));


//        log.info("year: {}, month: {}", year, month);
//        log.info("memberCardPerformance: {}", memberCardPerformance);
        //payments 모두 계산
        MemberCardPaymentInfos memberCardPaymentInfos = saveMemberCardPaymentsFromFinancial(memberCardInfo, memberCardInfo.getFinancialUserCardId(), memberCardPerformance.getLastFinancialId());

        if(memberCardPaymentInfos.getPayments().isEmpty()){
//            log.info("empty payment list");
            return makeMemberCardPaymentGetStatusResponse(memberCardInfo, memberCardPerformance);
        }
        int totalAmount = memberCardPerformance.getAmount() + memberCardPaymentInfos.getTotalAmount();
//        log.info("total amount {}", totalAmount);
        int lastId = memberCardPerformance.getLastFinancialId();
        lastId = memberCardPaymentInfos.getPayments().stream()
                .map(Payment::getFinancialMemberCardPaymentId)
                .max(Integer::compareTo).get();
//        log.info("last id {}", lastId);

        memberCardPerformance.setLastFinancialId(lastId, totalAmount);

        //실적 구간 찾기
        MemberCardPaymentGetStatusResponse memberCardPaymentGetStatusResponse = makeMemberCardPaymentGetStatusResponse(memberCardInfo, memberCardPerformance);
        memberCardPerformance.setCardPerformance(memberCardPaymentGetStatusResponse.getCardPerformanceId(), memberCardPaymentGetStatusResponse.getPerformanceLevel());
        return memberCardPaymentGetStatusResponse;
    }

    private MemberCardPaymentGetStatusResponse makeMemberCardPaymentGetStatusResponse(MemberCardInfo memberCardInfo, MemberCardPerformance memberCardPerformance){
        //실적 구간 찾기
        List<CardPerformance> cardPerformances = cardPerformanceRepository.findByCardVersion(memberCardInfo.getCardVersion());
        if(cardPerformances.isEmpty()){
            throw new MemberCardPaymentGetStatusException(ErrorCode.CARD_NOT_FOUND, "카드 버전이 존재하지 않습니다.");
        }

        int level = 0;
        int startAmount = 0;
        int endAmount = 0;
        int cardPerformanceId = 0;

        for(int i=0;i<cardPerformances.size();i++){
            CardPerformance cardPerformance = cardPerformances.get(i);
            cardPerformanceId = cardPerformance.getId();
            endAmount = cardPerformance.getAmount();
            if(memberCardPerformance.getAmount() >= startAmount){
                if(memberCardPerformance.getAmount() < endAmount){
                    level = cardPerformance.getLevel();
                    break;
                }
                else if(i == cardPerformances.size()-1){
                    level = cardPerformance.getLevel();
                    break;
                }
                startAmount = cardPerformance.getAmount();
            }
        }

        MemberCardPaymentGetStatusResponse memberCardPaymentGetStatusResponse =
                MemberCardPaymentGetStatusResponse.of(cardPerformanceId, memberCardPerformance.getAmount(),startAmount, endAmount, level);

        return memberCardPaymentGetStatusResponse;
    }

    //financial에서 추가된 이용 내역을 불러와 저장하는 메서드
    private MemberCardPaymentInfos saveMemberCardPaymentsFromFinancial(MemberCardInfo memberCardInfo, long bankUserCardId, int lastId ) {
        String requestUri = GET_MEMBER_CARD_PAYMENT + "?bankUserCardId=" + bankUserCardId + "&lastId=" + lastId;

        log.info("requestUri: {}", requestUri);
        String response = restClient.get()
                .uri(requestUri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new MemberCardCreateMemberCardsException(ErrorCode.NOT_FOUND_FINANCIAL_USER_CARD_ID, "financial에 해당하는 user card id가 없습니다");
                })
                .body(String.class);

        MemberCardPaymentInfos memberCardPaymentInfos = parsingGetAllRenewalMemberCardsFromJson(response, memberCardInfo);
        List<Payment> payments = paymentRepository.saveAll(memberCardPaymentInfos.getPayments());
//        log.info("payments size: {}", payments.size());
        return MemberCardPaymentInfos.of(payments, memberCardPaymentInfos.getTotalAmount());
    }

    private MemberCardPaymentInfos parsingGetAllRenewalMemberCardsFromJson(String response, MemberCardInfo memberCardInfo){
        ObjectMapper objectMapper = new ObjectMapper();

        List<Payment> payments = new ArrayList<>();
        List<Payment> savedPayments = new ArrayList<>();
        int totalAmount = 0;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode dataNode = jsonNode.get("data");
            JsonNode bankUserCardPaymentGetResponses = dataNode.get("bankUserCardPaymentGetResponses");


            for (JsonNode bankUserCardPaymentGetResponse : bankUserCardPaymentGetResponses) {
                Integer financialMemberCardPaymentId = bankUserCardPaymentGetResponse.get("id").asInt();
                Integer amount = bankUserCardPaymentGetResponse.get("amount").asInt();
                String paymentDateStr = bankUserCardPaymentGetResponse.get("paymentDate").asText();
                String paymentName = bankUserCardPaymentGetResponse.get("paymentName").asText();
                String paymentCategory = bankUserCardPaymentGetResponse.get("paymentCategory").asText();

//                log.info("paymentDateStr: {}", paymentDateStr);
                if (paymentRepository.existsByFinancialMemberCardPaymentId(financialMemberCardPaymentId)) {
//                    log.info("이미 있음!!");
                    continue;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ H:mm][ HH:mm]");
                LocalDateTime paymentDate = LocalDateTime.parse(paymentDateStr, formatter);


                Payment payment = Payment.of(memberCardInfo, financialMemberCardPaymentId, amount, paymentDate, paymentName, paymentCategory);

                payments.add(payment);
                //이번달 것만 +
                if(paymentDate.getYear() == LocalDate.now().getYear() && paymentDate.getMonthValue() == LocalDate.now().getMonthValue()){
                    totalAmount += payment.getAmount();
                }
            }

//            savedPayments = paymentRepository.saveAll(payments);

        } catch (Exception e) {
            log.error("json 파싱 실패!! {}", e.getMessage());
            e.printStackTrace();
            throw new MemberCardPaymentGetStatusException(ErrorCode.JSON_PARSING_ERROR, "JSON 변환에 실패했습니다.");
        }
        return MemberCardPaymentInfos.of(payments, totalAmount);
    }

}
