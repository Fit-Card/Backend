package com.fitcard.domain.membercard.performance.service;

import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.card.performance.repository.CardPerformanceRepository;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.domain.membercard.payment.exception.MemberCardPaymentGetStatusException;
import com.fitcard.domain.membercard.payment.model.Payment;
import com.fitcard.domain.membercard.performance.model.MemberCardPerformance;
import com.fitcard.domain.membercard.performance.repository.MemberCardPerformanceRepository;
import com.fitcard.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberCardPerformanceServiceImpl implements MemberCardPerformanceService {

    private final MemberCardPerformanceRepository memberCardPerformanceRepository;
    private final CardPerformanceRepository cardPerformanceRepository;


    @Override
    public MemberCardPerformance createMemberCardPerformance(MemberCardInfo memberCardInfo, List<Payment> payments, int year, int month) {
        int lastFinancialId = 0;
        int amount = 0;

        for(Payment payment : payments) {
            lastFinancialId = Math.max(lastFinancialId, payment.getFinancialMemberCardPaymentId());
            amount += payment.getAmount();
        }
        CardPerformance cardPerformance = findCardPerformance(memberCardInfo, amount);
        return memberCardPerformanceRepository.save(MemberCardPerformance.of(amount, year, month, memberCardInfo, lastFinancialId, cardPerformance));
    }

    private CardPerformance findCardPerformance(MemberCardInfo memberCardInfo, int amount){
        //실적 구간 찾기
        List<CardPerformance> cardPerformances = cardPerformanceRepository.findByCardVersion(memberCardInfo.getCardVersion());
        if(cardPerformances.isEmpty()){
            throw new MemberCardPaymentGetStatusException(ErrorCode.CARD_NOT_FOUND, "카드 버전이 존재하지 않습니다.");
        }

        int level = 0;
        int startAmount = 0;
        int endAmount = 0;
        int cardPerformanceId = 0;
        CardPerformance result = null;

        for(int i=0;i<cardPerformances.size();i++){
            CardPerformance cardPerformance = cardPerformances.get(i);
            result = cardPerformance;
            cardPerformanceId = cardPerformance.getId();
            endAmount = cardPerformance.getAmount();
            if(amount >= startAmount){
                if(amount < endAmount){
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
        return result;
    }
}
