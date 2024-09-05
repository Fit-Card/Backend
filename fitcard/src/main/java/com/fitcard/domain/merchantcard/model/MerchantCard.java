package com.fitcard.domain.merchantcard.model;

import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "merchant_card")
@Getter
@NoArgsConstructor
public class MerchantCard {

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
    private MerchantCard(MerchantInfo merchantId, CardVersion cardVersion) {
        this.merchantId = merchantId;
        this.cardVersion = cardVersion;
    }
}
