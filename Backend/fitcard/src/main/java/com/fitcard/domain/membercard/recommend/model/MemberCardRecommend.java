package com.fitcard.domain.membercard.recommend.model;

import com.fitcard.domain.card.performance.model.CardPerformance;
import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.global.common.BaseEntity;
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
public class MemberCardRecommend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberCardRecommendId;

    @NotNull
    private Integer recommendCardBenefitAmount;

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
    @JoinColumn(name = "recommend_card_performance_id", nullable = false)
    private CardPerformance recommendCardPerformance;

    public MemberCardRecommend(Integer recommendCardBenefitAmount, Integer memberCardBenefitAmount, Integer year, Integer month, MemberCardInfo memberCard, CardPerformance cardPerformance) {
        this.recommendCardBenefitAmount = recommendCardBenefitAmount;
        this.memberCardBenefitAmount = memberCardBenefitAmount;
        this.year = year;
        this.month = month;
        this.memberCard = memberCard;
        this.recommendCardPerformance = cardPerformance;
    }

    public static MemberCardRecommend of(MemberCardInfo memberCardInfo, CardPerformance cardPerformance, int memberCardBenefitAmount, int recommendCardBenefitAmount, int year, int month){
        return new MemberCardRecommend(recommendCardBenefitAmount, memberCardBenefitAmount, year, month, memberCardInfo, cardPerformance);
    }

}
