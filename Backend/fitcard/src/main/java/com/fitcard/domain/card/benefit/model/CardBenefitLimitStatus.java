package com.fitcard.domain.card.benefit.model;

import com.fitcard.global.util.LimitParser;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //todo: amount, count limit 한 번에 수정해야 한다.
    public void addAmount(int amount) {
        //todo: amount를 daily, total, monthly 등등에 limit 각각에 더해줘야한다.
//        this.nowAmount += amount;
//        if(this.nowAmount > cardBenefit.getAmountLimit()){
//            this.nowAmount = this.cardBenefit.getAmountLimit();
//        }
    }

    public void addCount() {
//        this.nowCount++;
    }
}
