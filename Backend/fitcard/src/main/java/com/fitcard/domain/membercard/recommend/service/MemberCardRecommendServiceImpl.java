package com.fitcard.domain.membercard.recommend.service;

import com.fitcard.domain.card.benefit.model.CardBenefitLimitStatus;
import com.fitcard.domain.card.benefit.repository.CardBenefitRepository;
import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.membercardinfo.repository.MemberCardInfoRepository;
import com.fitcard.domain.membercard.payment.model.Payment;
import com.fitcard.domain.membercard.payment.model.dto.request.MemberCardPaymentGetStatusRequest;
import com.fitcard.domain.membercard.payment.repository.PaymentRepository;
import com.fitcard.domain.membercard.payment.service.PaymentService;
import com.fitcard.domain.membercard.performance.model.MemberCardPerformance;
import com.fitcard.domain.membercard.performance.repository.MemberCardPerformanceRepository;
import com.fitcard.domain.membercard.performance.service.MemberCardPerformanceService;
import com.fitcard.domain.membercard.recommend.model.MemberCardRecommend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

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


    /**
     * 한 달에 한 번 모든 사용자 카드에 대해서 추천 카드를 저장한다.
     */
    @Override
    public void createMemberCardRecommend() {
        LocalDateTime beforeMonth = LocalDateTime.now().minusMonths(1);
        int year = beforeMonth.getYear();
        int month = beforeMonth.getMonthValue();
        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59, 59);

        List<MemberCardInfo> memberCardInfos = memberCardInfoRepository.findAll();

        for(MemberCardInfo memberCardInfo : memberCardInfos){
            //payment 계산
            paymentService.getMemberCardPaymentStatus(new MemberCardPaymentGetStatusRequest(memberCardInfo.getMemberCardId()));
            List<Payment> payments = paymentRepository.findAllByMemberCardAndPaymentDateBetween(memberCardInfo, startDateTime, endDateTime);

            //memberCardPerformance가 없다면 저장
            MemberCardPerformance memberCardPerformance = memberCardPerformanceRepository.findByMemberCardAndYearAndMonth(memberCardInfo, year, month)
                    .orElse(memberCardPerformanceService.createMemberCardPerformance(memberCardInfo, payments, year, month));

            log.info("payments: {}",payments);
            log.info("memberCardPerformance: {}",memberCardPerformance);
            //사용금액이 0원인 경우 해당 카드에 대해서 카드 추천 불가능
            if (memberCardPerformance.getAmount() == 0) {
                continue;
            }

            MemberCardRecommend recommendCard = findRecommendCard(memberCardPerformance.getAmount(), payments);
        }

    }

    private MemberCardRecommend findRecommendCard(int amount, List<Payment> payments){
        //모든 카드에 대해 할인 금액 계산해야 한다.
        //모든 카드 = 버전이 가장 최신이면서 performance가 사용자가 사용한 amount 구간인 CardPerformance를 말함
        List<CardPerformance> cardPerformances = cardPerformanceRepository.findNewestVersionAndMaxAmount(amount);

        log.info("cardPerformance: {}", cardPerformances);
        CardPerformance bestCardPerformance;
        int benefitDiff = 0;
        for(CardPerformance cardPerformance : cardPerformances){

        }

        return null;
    }

    private int calBenefitDifference(List<Payment> payments, CardPerformance cardPerformance) {
        //1. 카드의 혜택 목록 불러오기
        List<CardBenefitLimitStatus> cardBenefitLimitStatuses = cardBenefitRepository.findAllByCardPerformance(cardPerformance).stream()
                .map(CardBenefitLimitStatus::from)
                .toList();

        //2. 이용내역 각각을 혜택 목록에 대입해 할인 금액 계산하기
        // 이때 CardPerformance의 할인한도, CardBenefit 각각의 금액한도, 횟수한도를 넘으면 안된다.
        // 최소 결제 금액 수치와 예외 타입 리스트도 확인 필요

        int benefitAmount = 0;
        for (Payment payment : payments) {
            //이용내역 이름이 cardBenefits의 가맹점을 포함하고 있다면 할인 되는 것. (startWith) -> 완탐
            //아닐 경우에 payment의 카테고리 확인, 혜택에 카테고리 전체가 포함되는지 확인 -> 완탐
        }

        return benefitAmount;
    }


}
