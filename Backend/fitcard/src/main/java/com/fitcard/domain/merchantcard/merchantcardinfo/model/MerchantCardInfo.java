package com.fitcard.domain.merchantcard.merchantcardinfo.model;

import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merchant_card")
@Getter
@NoArgsConstructor
public class MerchantCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantCardId;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private MerchantInfo merchantId;

    @ManyToOne
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion cardVersion;

    // private 생성자
    private MerchantCardInfo(MerchantInfo merchantId, CardVersion cardVersion) {
        this.merchantId = merchantId;
        this.cardVersion = cardVersion;
    }
}
