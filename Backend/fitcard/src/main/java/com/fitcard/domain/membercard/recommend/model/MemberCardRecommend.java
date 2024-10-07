package com.fitcard.domain.membercard.recommend.model;

import com.fitcard.domain.card.performance.model.CardPerformance;
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
    @JoinColumn(name = "recommand_card_performance_id", nullable = false)
    private CardPerformance cardPerformance;

    public MemberCardRecommend(Integer benefitDifference, Integer memberCardBenefitAmount, Integer year, Integer month, MemberCardInfo memberCard, CardPerformance cardPerformance) {
        this.benefitDifference = benefitDifference;
        this.memberCardBenefitAmount = memberCardBenefitAmount;
        this.year = year;
        this.month = month;
        this.memberCard = memberCard;
        this.cardPerformance = cardPerformance;
    }

    public static MemberCardRecommend of(MemberCardInfo memberCardInfo, CardPerformance cardPerformance, int memberCardBenefitAmount, int benefitDifference, int year, int month){
        return new MemberCardRecommend(benefitDifference, memberCardBenefitAmount, year, month, memberCardInfo, cardPerformance);
    }

}
