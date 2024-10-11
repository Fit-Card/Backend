package com.fitcard.domain.merchantcard.merchantcardinfo.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(name = "가맹점-카드 은행사 조회 응답 DTO", description = "가맹점-카드 은행사 조회 응답 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MerchantCardBankResponse {
    @Schema(description = "은행사 ID", example = "1")
    private Integer cardCompanyId;

    @Schema(description = "은행사 이름", example = "신한은행")
    private String bankName;

    @Schema(description = "은행사 개수", example = "2")
    private int count;

    @Schema(description = "은행사 이미지", example = "https://incongruous-cheshire-d58.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2Fabaad549-974c-4139-b3a0-5f2f367aa439%2Fecead14b-fb4f-4744-887b-732f72dd1c24%2Fsh.png?table=block&id=98a2327a-124b-4686-ba56-1fa3ad911fb4&spaceId=abaad549-974c-4139-b3a0-5f2f367aa439&width=1420&userId=&cache=v2")
    private String bankImgUrl;
    public static MerchantCardBankResponse from(Integer cardCompanyId, String bankName, int count, String bankImgUrl){
        return new MerchantCardBankResponse(cardCompanyId, bankName, count, bankImgUrl);
    }
}