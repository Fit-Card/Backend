package com.fitcard.domain.card.version.model;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "card_version")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardVersion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "card_info_id", nullable = false)
    private CardInfo cardInfo;

    @NotNull
    private int annualFee;

    @NotEmpty
    private String imageUrl;

    @NotNull
    private int version;

    //todo: 추후 CardVersion 저장 완료되면 삭제 예정
    public static CardVersion empty() {
        return new CardVersion();
    }
}
