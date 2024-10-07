package com.fitcard.domain.membercard.recommend.model;

import com.fitcard.domain.card.cardinfo.model.CardInfo;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Table(name = "member_card_recommend")
@Getter
@NoArgsConstructor
public class MemberCardRecommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberCardRecommendId;

    @NotNull
    private Integer benefitDifference;

    @NotNull
    private Integer memberCardBenefitAmount;

    @NotNull
    private Integer year;

    @NotNull
    private Integer month;

    @ManyToOne
    @JoinColumn(name = "member_card_id", nullable = false)
    private MemberCardInfo memberCard;

    @ManyToOne
    @JoinColumn(name = "recommand_card_id", nullable = false)
    private CardInfo cardInfo;
}
