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
    @JoinColumn(name = "merchant", nullable = false)
    private MerchantInfo merchantId;

    @ManyToOne
    @JoinColumn(name = "card_version", nullable = false)
    private CardVersion cardVersionId;

    @ManyToOne
    @JoinColumn(name = "card_version", nullable = false)
    private CardVersion cardId2;

    @ManyToOne
    @JoinColumn(name = "card_version", nullable = false)
    private CardVersion cardCompanyId;

    // private 생성자
    private MerchantCard(MerchantInfo merchantId, CardVersion cardVersionId, CardVersion cardId2, CardVersion cardCompanyId) {
        this.merchantId = merchantId;
        this.cardVersionId = cardVersionId;
        this.cardId2 = cardId2;
        this.cardCompanyId = cardCompanyId;
    }
}
