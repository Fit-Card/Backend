package com.fitcard.domain.event.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class FinancialCardEventResponse {

    @NotNull
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
    private LocalDate startDate;

    @NotEmpty
    private LocalDate endDate;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}
