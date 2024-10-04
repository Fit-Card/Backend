package com.fitcard.global.firebase.dto;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FirebaseCardInfoRequest {

    @NotEmpty
    private String financialCardId;

    @NotNull
    private boolean isCredit;

    @NotNull
    private boolean isPersonal;

    @NotNull
    private boolean isBC;

    @NotNull
    private String cardCompanyName;

    @NotEmpty
    private String cardName;

    public static FirebaseCardInfoRequest from(MemberCardInfo memberCardInfo) {
        return new FirebaseCardInfoRequest(
                memberCardInfo.getCardVersion().getCardInfo().getFinancialCardId(),
                memberCardInfo.getCardVersion().getCardInfo().isCredit(), // 체크/신용 여부
                memberCardInfo.getIsPersonal(), // 개인/가족 여부
                memberCardInfo.getCardVersion().getCardInfo().isBC(), // BC 여부
                memberCardInfo.getCardVersion().getCardInfo().getCardCompany().getName(), // 카드사 이름
                memberCardInfo.getCardVersion().getCardInfo().getName() // 카드 이름
        );
    }
}
