package com.fitcard.domain.card.performance.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class FinancialCardPerformanceResponse {
    private Long cardPerformanceId;

    @NotEmpty
    private String cardId;

    @NotNull
    private int level;

    @NotNull
    private int amount;

    @NotNull
    private int benefitLimit;

    @NotNull
    private String updatedAt;
}
