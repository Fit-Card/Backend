package com.financial.domain.bank.cardperformance.model.dto.response;

import com.financial.domain.bank.cardperformance.model.BankCardPerformance;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BankCardPerformanceGetResponse {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Long cardPerformanceId;

    @NotEmpty
    private String CardId;

    @NotNull
    private int level;

    @NotNull
    private int amount;

    @NotNull
    private int benefitLimit;

    @NotNull
    private String updatedAt;

    public static BankCardPerformanceGetResponse of(BankCardPerformance performance) {
        return new BankCardPerformanceGetResponse(
                performance.getCardPerformanceId(),
                performance.getBankCard().getCardId(),
                performance.getLevel(),
                performance.getAmount(),
                performance.getBenefitLimit(),
                performance.getUpdatedAt().format(formatter));
    }
}
