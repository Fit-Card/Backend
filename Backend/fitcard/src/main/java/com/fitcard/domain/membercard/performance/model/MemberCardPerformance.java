package com.fitcard.domain.membercard.performance.model;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Table(name = "member_card_performance")
@Getter
@NoArgsConstructor
public class MemberCardPerformance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberCardPerformanceId;

    @NotNull
    private Integer amount;

    @NotNull
    private Integer year;

    @NotNull
    private Integer month;

    @NotNull
    private Integer lastFinancialId;

    @NotNull
    private Integer cardPerformanceId;

    @NotNull
    private Integer cardPerformanceLevel;

    @ManyToOne
    @JoinColumn(name = "member_card_id", nullable = false)
    private MemberCardInfo memberCard;

    private MemberCardPerformance(Integer amount, Integer year, Integer month, MemberCardInfo memberCard, Integer cardPerformanceId, Integer cardPerformanceLevel) {
        this.amount = amount;
        this.year = year;
        this.month = month;
        this.memberCard = memberCard;
        this.lastFinancialId = 0;
        this.cardPerformanceId = cardPerformanceId;
        this.cardPerformanceLevel = cardPerformanceLevel;
    }

    public static MemberCardPerformance empty(Integer year, Integer month, MemberCardInfo memberCardInfo) {
        return new MemberCardPerformance(0, year, month, memberCardInfo, 0, 1);
    }

    public void setLastFinancialId(Integer lastFinancialId, Integer amount) {
        this.lastFinancialId = lastFinancialId;
        this.amount = amount;
    }

    public void setCardPerformance(Integer cardPerformanceId, Integer cardPerformanceLevel) {
        this.cardPerformanceId = cardPerformanceId;
        this.cardPerformanceLevel = cardPerformanceLevel;
    }
}
