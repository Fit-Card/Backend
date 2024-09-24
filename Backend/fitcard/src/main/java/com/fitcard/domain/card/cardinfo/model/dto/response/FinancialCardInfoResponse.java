package com.fitcard.domain.card.cardinfo.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FinancialCardInfoResponse {
    private String cardId;

    @NotEmpty
    private String cardCompanyId;

    @NotBlank
    private String cardName;

    @NotNull
    private Integer annualFee;

    @NotNull
    private Integer abroadAnnualFee;

    @NotNull
    private boolean createCheckType;

    @NotNull
    private boolean isBC;

    @NotNull
    private String cardImageUrl;

    @NotNull
    private String updatedAt;
}
