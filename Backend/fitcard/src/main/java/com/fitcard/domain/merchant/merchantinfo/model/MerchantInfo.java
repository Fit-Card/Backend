package com.fitcard.domain.merchant.merchantinfo.model;

import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Table(name = "merchant")
@Getter
@NoArgsConstructor
public class MerchantInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String category;

    private String url;

    // private 생성자
    private MerchantInfo(String name, String category, String url) {
        this.name = name;
        this.category = category;
        this.url = url;
    }

    public static MerchantInfo of(String merchantName, String merchantCategory, String merchantUrl) {
        return new MerchantInfo(merchantName, merchantCategory, merchantUrl);
    }
}
