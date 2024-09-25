package com.financial.domain.bank.usercard.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BankUserCardDeleteRequest {

    @NotNull
    private Long bankUserCardId;
}
