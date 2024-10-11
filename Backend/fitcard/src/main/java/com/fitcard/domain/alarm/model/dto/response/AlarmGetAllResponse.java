package com.fitcard.domain.alarm.model.dto.response;

import com.fitcard.domain.alarm.model.Alarm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Schema(name = "알람 전체 조회 응답 dto", description = "알람 전체 조회 응답 DTO")
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AlarmGetAllResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Schema(description = "카드 이벤트 id", example ="1")
    private Long cardEventId;

    @Schema(description = "카드 이름", example = "신한 트래블 SOL")
    private String cardName;

    @Schema(description = "카드 이미지", example = "https://www.shinhancard.com/pconts/images/contents/card/plate/cdCheckBGND9K.png")
    private String cardImage;

    @Schema(description = "알람 제목", example = "신한 트래블 SOL과 함께 여행을 떠나요")
    private String alarmTitle;

    @Schema(description = "알람 생성 시간", example = "2024-09-06 17:51:02")
    private String alarmCreatedAt;

    public static AlarmGetAllResponse of(Long cardEventId, String cardName, String cardImage, String alarmTitle, String alarmCreatedAt) {
        return new AlarmGetAllResponse(cardEventId, cardName, cardImage, alarmTitle, alarmCreatedAt);
    }
}
