package com.fitcard.domain.card.benefit.model;

import com.fitcard.domain.membercard.payment.model.Payment;
import com.fitcard.global.util.LimitParser;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CardBenefitLimitStatus {

    @NotNull
    private CardBenefit cardBenefit;

    @NotNull
    private BenefitAmountLimitType benefitAmountLimitType;

    @NotNull
    private int sessionLimitAmount;

    @NotNull
    private int dailyLimitAmount;

    @NotNull
    private int monthlyLimitAmount;

    @NotNull
    private int dailyLimitCount;

    @NotNull
    private int monthlyLimitCount;

    @NotNull
    private int[] dailyAmountStatus;

    @NotNull
    private int nowTotalAmountStatus;

    @NotNull
    private int[] dailyCountStatus;

    @NotNull
    private int nowTotalCountStatus;

    public static CardBenefitLimitStatus from(CardBenefit cardBenefit){
        BenefitAmountLimitType.Result result = BenefitAmountLimitType.parseAndFindType(cardBenefit.getAmountLimit());
        BenefitAmountLimitType limitType = result.getType();
        int sessionLimitAmount = result.getLimits()[0];
        int dailyLimitAmount = result.getLimits()[1];
        int monthlyLimitAmount = result.getLimits()[2];

        int[] dailyAmountStatus = new int[31];
        int[] dailyCountStatus = new int[31];

        int[] countLimits = LimitParser.parseLimits(cardBenefit.getCountLimit());
        int dailyLimitCount = countLimits[0];
        int monthlyLimitCount = countLimits[1];

        return new CardBenefitLimitStatus(cardBenefit, limitType, sessionLimitAmount, dailyLimitAmount, monthlyLimitAmount,
                dailyLimitCount, monthlyLimitCount, dailyAmountStatus, 0, dailyCountStatus, 0);
    }

    public void addDiscount(Payment payment) {
        //최소 결제 금액 확인
        if(this.cardBenefit.getMinPayment() != 0 && this.cardBenefit.getMinPayment() > payment.getAmount()) return;

        int discountAmount = BenefitType.getBenefitTypeByName(cardBenefit.getBenefitType())
                .calculateBenefit(new BenefitType.BenefitInput(payment.getAmount(), this.cardBenefit.getBenefitValue(), this.cardBenefit.getBenefitPer()));

        //할인 금액 비교
        // 회 당 받을 수 있는 최대 할인 금액
        if(this.sessionLimitAmount != 0 && this.sessionLimitAmount < discountAmount) {
            discountAmount = this.sessionLimitAmount;
        }

        //daily amount limit 비교 후 받을 수 있는 최대 할인 금액 계산
        int day = payment.getPaymentDate().getDayOfMonth();
        int totalDailyDiscount = dailyAmountStatus[day] + discountAmount;
        if(dailyLimitAmount != 0 && dailyLimitAmount < totalDailyDiscount) { //오늘 받을 수 있는 최대 할인 계산
            discountAmount = dailyLimitAmount - dailyAmountStatus[day];
            totalDailyDiscount = dailyLimitAmount;
        }

        //monthly amount limit 비교 후 받을 수 있는 최대 할인 금액 계산
        if(monthlyLimitAmount != 0 && monthlyLimitAmount < nowTotalAmountStatus + discountAmount){
            discountAmount = monthlyLimitAmount - nowTotalAmountStatus;
        }

        //횟수 비교
        if(dailyLimitCount != 0 && dailyLimitCount < dailyCountStatus[day]+1) return;
        if(monthlyLimitCount != 0 && monthlyLimitCount < nowTotalCountStatus+1) return;

        //할인 가능
        dailyAmountStatus[day] += discountAmount;
        dailyCountStatus[day] ++;
        nowTotalAmountStatus += discountAmount;
        nowTotalCountStatus++;
    }
}
