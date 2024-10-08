package com.fitcard.domain.membercard.recommend.service;

import com.fitcard.domain.card.benefit.model.CardBenefitLimitStatus;
import com.fitcard.domain.card.benefit.repository.CardBenefitRepository;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.member.model.Member;
import com.fitcard.domain.member.repository.MemberRepository;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.membercardinfo.repository.MemberCardInfoRepository;
import com.fitcard.domain.membercard.payment.model.Payment;
import com.fitcard.domain.membercard.payment.model.dto.request.MemberCardPaymentGetStatusRequest;
import com.fitcard.domain.membercard.payment.repository.PaymentRepository;
import com.fitcard.domain.membercard.payment.service.PaymentService;
import com.fitcard.domain.membercard.performance.model.MemberCardPerformance;
import com.fitcard.domain.membercard.performance.repository.MemberCardPerformanceRepository;
import com.fitcard.domain.membercard.performance.service.MemberCardPerformanceService;
import com.fitcard.domain.membercard.recommend.exception.MemberCardRecommendGetAllException;
import com.fitcard.domain.membercard.recommend.model.MemberCardRecommend;
import com.fitcard.domain.membercard.recommend.model.dto.response.MemberCardRecommendResponse;
import com.fitcard.domain.membercard.recommend.model.dto.response.MemberCardRecommendResponses;
import com.fitcard.domain.membercard.recommend.repository.MemberCardRecommendRepository;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.domain.merchant.merchantinfo.repository.MerchantInfoRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCardRecommendServiceImpl implements MemberCardRecommendService{

    private final MemberCardInfoRepository memberCardInfoRepository;
    private final PaymentRepository paymentRepository;
    private final MemberCardPerformanceRepository memberCardPerformanceRepository;
    private final CardPerformanceRepository cardPerformanceRepository;
    private final CardBenefitRepository cardBenefitRepository;
    private final PaymentService paymentService;
    private final MemberCardPerformanceService memberCardPerformanceService;
    private final MerchantInfoRepository merchantInfoRepository;
    private final MemberCardRecommendRepository memberCardRecommendRepository;
    private final MemberRepository memberRepository;


    /**
     * 한 달에 한 번 모든 사용자 카드에 대해서 추천 카드를 저장한다.
     */
    @Override
    @Synchronized
    public void createMemberCardRecommend() {
        LocalDateTime beforeMonth = LocalDateTime.now().minusMonths(1);
        int year = beforeMonth.getYear();
        int month = beforeMonth.getMonthValue();
        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59, 59);

        List<MemberCardInfo> memberCardInfos = memberCardInfoRepository.findAll();
//        log.info("memberCardInfos: {}", memberCardInfos);
        for(MemberCardInfo memberCardInfo : memberCardInfos){
            //payment 계산
            paymentService.getMemberCardPaymentStatus(new MemberCardPaymentGetStatusRequest(memberCardInfo.getMemberCardId()));
            List<Payment> payments = paymentRepository.findAllByMemberCardAndPaymentDateBetween(memberCardInfo, startDateTime, endDateTime);

            //memberCardPerformance가 없다면 저장
            MemberCardPerformance memberCardPerformance = memberCardPerformanceRepository.findByMemberCardAndYearAndMonth(memberCardInfo, year, month)
                    .orElseGet(()->memberCardPerformanceService.createMemberCardPerformance(memberCardInfo, payments, year, month));

            Optional<CardPerformance> optionalCardPerformance = cardPerformanceRepository.findById(memberCardPerformance.getCardPerformanceId());
            if(optionalCardPerformance.isEmpty()){
                continue;
            }
            CardPerformance cardPerformance = optionalCardPerformance.get();

            //사용금액이 0원인 경우 해당 카드에 대해서 카드 추천 불가능
            if (memberCardPerformance.getAmount() == 0) {
                continue;
            }

            MemberCardRecommend recommendCard = findRecommendCard(memberCardPerformance.getAmount(), payments, cardPerformance, memberCardInfo, year, month);
            memberCardRecommendRepository.save(recommendCard);
        }

    }

    @Override
    public MemberCardRecommendResponses getMemberCardAllRecommend(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberCardRecommendGetAllException(ErrorCode.MEMBER_NOT_FOUND, "사용자가 존재하지 않습니다."));
        List<MemberCardInfo> memberCards = memberCardInfoRepository.findAllByMember(member);

        List<MemberCardRecommendResponse> memberCardRecommendResponses = memberCards.stream()
                .map(memberCard -> {
                    Optional<MemberCardRecommend> memberCardRecommend = memberCardRecommendRepository.findByMemberCard(memberCard);
                    return memberCardRecommend.map(MemberCardRecommendResponse::of).orElseGet(() -> MemberCardRecommendResponse.empty(memberCard));
                })
                .toList();
        return MemberCardRecommendResponses.from(memberCardRecommendResponses);
    }

    @Synchronized
    private MemberCardRecommend findRecommendCard(int amount, List<Payment> payments, CardPerformance memberCardPerformance, MemberCardInfo memberCardInfo, int year, int month){
        //1. memberCard의 혜택 계산
        int memberCardBenefit = calBenefit(payments, memberCardPerformance);

        //모든 카드에 대해 할인 금액 계산해야 한다.
        //모든 카드 = 버전이 가장 최신이면서 performance가 사용자가 사용한 amount 구간인 CardPerformance를 말함
        List<CardPerformance> cardPerformances = cardPerformanceRepository.findNewestVersionAndMaxAmount(amount);

        CardPerformance bestCardPerformance = null;
        int recommendCardBenefitAmount = 0;
//        int benefitDiff = -987654321;
        for(CardPerformance cardPerformance : cardPerformances){
            int nowCardBenefit = calBenefit(payments, cardPerformance);
            if(nowCardBenefit > recommendCardBenefitAmount){
                recommendCardBenefitAmount = nowCardBenefit;
                bestCardPerformance = cardPerformance;
            }
        }
        //todo : bestCardPerformance가 null 인 경우 예외처리 필요, null인 경우가 없을 것 같긴 하다.
        return MemberCardRecommend.of(memberCardInfo, bestCardPerformance, memberCardBenefit, recommendCardBenefitAmount, year, month);
    }

    @Synchronized
    private int calBenefit(List<Payment> payments, CardPerformance cardPerformance) {

        //1. 카드의 혜택 목록 불러오기
        List<CardBenefitLimitStatus> cardBenefitLimitStatuses = cardBenefitRepository.findAllByCardPerformance(cardPerformance).stream()
                .map(CardBenefitLimitStatus::from)
                .toList();

        //2. 이용내역 각각을 혜택 목록에 대입해 할인 금액 계산하기
        for (Payment payment : payments) {
            CardBenefitLimitStatus nowPaymentBenefit = null;
            int index = -1;
            //이용내역 이름이 cardBenefits의 가맹점을 포함하고 있다면 할인 되는 것. (startWith) -> 완탐
            int size = cardBenefitLimitStatuses.size();
            for(int i=0;i<size;i++){
                CardBenefitLimitStatus cardBenefitLimitStatus = cardBenefitLimitStatuses.get(i);
                //payment와 benefit의 카테고리 다른 경우
                if(!cardBenefitLimitStatus.getCardBenefit().getMerchantCategory().equals(payment.getPaymentCategory())) continue;

                MerchantInfo merchant = merchantInfoRepository.findByMerchantId(cardBenefitLimitStatus.getCardBenefit().getMerchantId());
                if(merchant == null) continue;
                
                //todo: payment name이 excludeType에 해당하는 merchant인지 확인필요 -> 잘려서 확인 불가능임. 어쩔수가 없다

                //merchantID가 1~6이라면 해당 카테고리 전체 혜택
                if(merchant.getMerchantId() <= 6){
                    nowPaymentBenefit = cardBenefitLimitStatus;
                    index = i;
//                    continue;
                }

                if(!payment.getPaymentName().contains(merchant.getName())) continue;
                if(nowPaymentBenefit != null && nowPaymentBenefit.getCardBenefit().getBenefitValue() < cardBenefitLimitStatus.getCardBenefit().getBenefitValue()){
                    nowPaymentBenefit = cardBenefitLimitStatus;
                    index = i;
                }
            }
            //해당하는 혜택이 없는 경우
            if(nowPaymentBenefit == null) continue;

            //할인금액 더하기
            int nowDiscountAmount = cardBenefitLimitStatuses.get(index).addDiscount(payment);
            // log.info("할인 금액: {}", nowDiscountAmount);
        }

        //3. list의 nowTotalAmountStatus 더해서 최종 할인 금액 계산
        int discountAmount = calDiscountAmount(cardBenefitLimitStatuses);

        return discountAmount;
    }

    int calDiscountAmount(List<CardBenefitLimitStatus> cardBenefitLimitStatuses) {
        int discountAmount = 0;
        for(CardBenefitLimitStatus cardBenefitLimitStatus : cardBenefitLimitStatuses){
            discountAmount += cardBenefitLimitStatus.getNowTotalAmountStatus();
        }
        return discountAmount;
    }

}
