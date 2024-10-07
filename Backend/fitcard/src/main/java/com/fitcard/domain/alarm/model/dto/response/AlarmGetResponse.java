package com.fitcard.domain.alarm.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Schema(name = "알람 상세 조회 응답 dto", description = "알람 상세 조회 응답 DTO")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmGetResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //카드 이름, 카드 이미지, 알람 제목, 생성 시간

    @Schema(description = "카드 이름", example = "신한 트래블 SOL")
    private String cardName;

    @Schema(description = "카드 이미지", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImage;

    @Schema(description = "알람 제목", example = "신한 트래블 SOL과 함께 여행을 떠나요")
    private String alarmTitle;

    @Schema(description = "알람 내용", example = "우와~ 신나는 9월~ 신한 트래블 SOL과 함께 여행을 떠나요!")
    private String alarmContent;

    @Schema(description = "대상 카드 목록", example = "신한 트래블 SOL, 신한 트래블 SOOL")
    private String targetCards;

    @Schema(description = "이벤트 시작일", example = "2024-09-01")
    private String eventStartDate;

    @Schema(description = "이벤트 종료일", example = "2024-09-20")
    private String eventEndDate;

    @Schema(description = "이벤트 Url", example = "https://www.shinhancard.com/pconts/html/benefit/event/1229096_2239.html")
    private String eventUrl;

    @Schema(description = "알람 생성 시간", example = "2024-09-06 17:51:02")
    private String alarmCreatedAt;

    public static AlarmGetResponse of(String cardName, String cardImage, String alarmTitle, String alarmContent, String targetCards,
                                      String eventStartDate, String eventEndDate, String eventUrl, String alarmCreatedAt) {
        return new AlarmGetResponse(
                cardName,
                cardImage,
                alarmTitle,
                alarmContent,
                targetCards,
                eventStartDate,
                eventEndDate,
                eventUrl,
                alarmCreatedAt
        );
    }
}
