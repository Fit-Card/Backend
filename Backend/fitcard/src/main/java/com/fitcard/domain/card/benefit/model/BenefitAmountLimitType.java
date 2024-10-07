package com.fitcard.domain.card.benefit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@ToString
@RequiredArgsConstructor
public enum BenefitAmountLimitType {
    PER_TRANSACTION_DISCOUNT_LIMIT("회 당 할인 한도"),
    PER_TRANSACTION_AND_MONTHLY_DISCOUNT_LIMIT("회당 할인, 월 할인 한도"),
    MONTHLY_DISCOUNT_LIMIT("월 할인 한도"),
    TRANSACTION_AMOUNT_DISCOUNT_LIMIT("이용 금액 할인 한도"),
    DAILY_DISCOUNT_LIMIT("일일 할인 한도"),
    DAILY_AND_MONTHLY_DISCOUNT_LIMIT("일일 할인 + 월 할인 한도"),
    NO_LIMIT("제한 없음");

    private final String description;


    /**
     * 주어진 문자열을 통해 BenefitAmountLimitType을 판별하고, 그에 맞는 limits를 반환
     *
     * @param input 파싱할 입력 문자열
     * @return Result: BenefitAmountLimitType과 한도 배열을 포함한 객체
     */
    public static Result parseAndFindType(String input) {
        // 유연한 회당(건당) 한도 파싱 (건당, 회당)
        Pattern perPattern = Pattern.compile("(건당|회당)\\s*(\\d+)원");
        // 일일 한도 파싱 (일)
        Pattern dailyPattern = Pattern.compile("일\\s*(\\d+)원");
        // 월 한도 파싱 (월)
        Pattern monthlyPattern = Pattern.compile("월\\s*(\\d+)원");
        // 특정한 이용금액 파싱
        Pattern specificPattern = Pattern.compile("(주유금액)?\\s*일\\s*(\\d+)원,?\\s*월\\s*(\\d+)원");

        int[] limits = new int[3]; // [회당 한도, 일일 한도, 월 한도]

        // 회당(건당) 한도 파싱
        Matcher perMatcher = perPattern.matcher(input);
        if (perMatcher.find()) {
            limits[0] = Integer.parseInt(perMatcher.group(2));  // 회당/건당 금액
        }

        // 일일 한도 파싱
        Matcher dailyMatcher = dailyPattern.matcher(input);
        if (dailyMatcher.find()) {
            limits[1] = Integer.parseInt(dailyMatcher.group(1));
        }

        // 월 한도 파싱
        Matcher monthlyMatcher = monthlyPattern.matcher(input);
        if (monthlyMatcher.find()) {
            limits[2] = Integer.parseInt(monthlyMatcher.group(1));
        }

        // 주유금액 같은 특정 패턴의 일/월 금액 파싱
        Matcher specificMatcher = specificPattern.matcher(input);
        if (specificMatcher.find()) {
            limits[1] = Integer.parseInt(specificMatcher.group(2));  // 일일 금액
            limits[2] = Integer.parseInt(specificMatcher.group(3));  // 월 금액
        }

        // 파싱된 한도 배열을 통해 BenefitAmountLimitType 결정
        BenefitAmountLimitType type = determineType(limits);
        return new Result(type, limits);
    }

    // 파싱된 한도 값에 따라 BenefitAmountLimitType을 결정하는 메서드
    private static BenefitAmountLimitType determineType(int[] limits) {
        if (limits[0] > 0 && limits[2] > 0) {
            return PER_TRANSACTION_AND_MONTHLY_DISCOUNT_LIMIT;
        } else if (limits[0] > 0) {
            return PER_TRANSACTION_DISCOUNT_LIMIT;
        } else if (limits[2] > 0) {
            return MONTHLY_DISCOUNT_LIMIT;
        } else if (limits[1] > 0 && limits[2] > 0) {
            return DAILY_AND_MONTHLY_DISCOUNT_LIMIT;
        } else if (limits[1] > 0) {
            return DAILY_DISCOUNT_LIMIT;
        } else {
            return NO_LIMIT;
        }
    }

    // BenefitAmountLimitType과 파싱된 한도를 함께 반환하는 클래스
    @Getter
    @RequiredArgsConstructor
    public static class Result {
        private final BenefitAmountLimitType type;
        private final int[] limits;
    }
}
