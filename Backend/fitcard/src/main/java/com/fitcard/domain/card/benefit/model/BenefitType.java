package com.fitcard.domain.card.benefit.model;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BenefitType {
    PERCENT_DISCOUNT( // % 할인
            (input) -> (input.amount * input.benefitValue) / 100
    ),
    PERCENT_REWARD( // % 적립
            (input) -> (input.amount * input.benefitValue) / 100
    ),
    PERCENT_CASHBACK( // % 캐쉬백
            (input) -> (input.amount * input.benefitValue) / 100
    ),
    MILEAGE_REWARD( // 고정 마일리지 적립
            (input) -> input.benefitValue
    ),
    AMOUNT_DISCOUNT( // 고정 금액 할인
            (input) -> input.benefitValue
    ),
    POINT_REWARD( // 고정 포인트 적립
            (input) -> input.benefitValue
    ),
    AMOUNT_CASHBACK( // 고정 금액 캐쉬백
            (input) -> input.benefitValue
    ),
    LITER_DISCOUNT( // 리터당 할인
            // todo : 추후 그달의 평균 기름 값 크롤링해 변경
            (input) -> (input.amount / 1550) * input.benefitValue
    ),
    OTHER_BENEFITS( // 기타 혜택은 계산이 필요 없다고 가정
            (input) -> 0.0
    );

    private static final Map<String, BenefitType> benefitTypeMap = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Enum::name, Function.identity()))
    );

    private final Function<BenefitInput, Double> calculateBenefitOp;

    BenefitType(Function<BenefitInput, Double> calculateBenefitOp) {
        this.calculateBenefitOp = calculateBenefitOp;
    }

    public int calculateBenefit(BenefitInput input) {
        return calculateBenefitOp.apply(input).intValue();
    }

    // 할인 계산에 필요한 입력값을 포함하는 클래스
    public static class BenefitInput {
        public final int amount;
        public final double benefitValue;
        public final int benefitPer;

        public BenefitInput(int amount, double benefitValue, int benefitPer) {
            this.amount = amount;
            this.benefitValue = benefitValue;
            this.benefitPer = benefitPer;
        }
    }

    // String name을 기준으로 BenefitType을 찾는 메서드
    public static BenefitType getBenefitTypeByName(String name) {
        return benefitTypeMap.getOrDefault(name, OTHER_BENEFITS);
    }
}
