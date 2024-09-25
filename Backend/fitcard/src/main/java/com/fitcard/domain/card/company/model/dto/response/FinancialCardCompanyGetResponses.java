package com.fitcard.domain.card.company.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "카드사 전체 조회 목록 DTO")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class FinancialCardCompanyGetResponses {

    @NotNull
    private List<FinancialCardCompanyGetResponse> cardCompanies;

    @Schema(description = "목록 개수", example = "1")
    private int size;

    public static FinancialCardCompanyGetResponses from(List<FinancialCardCompanyGetResponse> cardCompanies) {
        return new FinancialCardCompanyGetResponses(cardCompanies, cardCompanies.size());
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FinancialCardCompanyGetResponse {
        private String companyId;

        private String companyName;

        private String companyImageUrl;
    }
}
