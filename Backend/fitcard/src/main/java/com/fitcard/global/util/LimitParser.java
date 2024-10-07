package com.fitcard.global.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LimitParser {

    /**
     * 주어진 문자열을 통해 일일 및 월간 제한 횟수를 파싱
     *
     * @param input 파싱할 입력 문자열
     * @return int[]: [dailyLimitCount, monthlyLimitCount]
     */
    public static int[] parseLimits(String input) {
        int dailyLimitCount = 0;
        int monthlyLimitCount = 0;

        // 정규식 패턴
        Pattern dailyPattern = Pattern.compile("일\\s*(\\d+)회");
        Pattern monthlyPattern = Pattern.compile("월\\s*(\\d+)회");

        // "일 n회" 파싱
        Matcher dailyMatcher = dailyPattern.matcher(input);
        if (dailyMatcher.find()) {
            dailyLimitCount = Integer.parseInt(dailyMatcher.group(1));
        }

        // "월 n회" 파싱
        Matcher monthlyMatcher = monthlyPattern.matcher(input);
        if (monthlyMatcher.find()) {
            monthlyLimitCount = Integer.parseInt(monthlyMatcher.group(1));
        }

        // 결과 배열 반환
        return new int[]{dailyLimitCount, monthlyLimitCount};
    }
}