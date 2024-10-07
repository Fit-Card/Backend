package com.financial.domain.bank.cardevent.model.dto.response;

import com.financial.domain.bank.cardevent.model.BankCardEvent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardEventGetResponse {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long cardEventId;

    @NotEmpty
    private String cardCompanyId;

    @NotEmpty
    private String target;

    @NotNull
    private Boolean isCategory;

    @NotEmpty
    private String eventUrl;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String endDate;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    public static BankCardEventGetResponse of(BankCardEvent bankCardEvent) {
        return new BankCardEventGetResponse(
                bankCardEvent.getCardEventId(),
                bankCardEvent.getFinCardCompany().getId(),
                bankCardEvent.getTarget(),
                bankCardEvent.getIsCategory(),
                bankCardEvent.getEventUrl(),
                bankCardEvent.getStartDate(),
                bankCardEvent.getEndDate(),
                bankCardEvent.getTitle(),
                bankCardEvent.getContent()
        );
    }
}
