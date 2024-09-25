package com.financial.domain.bank.usercard.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BankUserCardSaveRequest {

    @NotBlank
    private String bankCardId;

    @NotBlank
    private String finUserId;

    @NotBlank
    private String globalBrand;

    @NotBlank
    private String expiredDate;

    @NotBlank
    private char cardMemberType;
}
