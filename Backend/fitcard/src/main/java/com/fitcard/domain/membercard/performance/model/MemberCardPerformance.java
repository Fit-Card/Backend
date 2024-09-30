package com.fitcard.domain.membercard.performance.model;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "member_card_id", nullable = false)
    private MemberCardInfo memberCard;

    private MemberCardPerformance(Integer amount, Integer year, Integer month, MemberCardInfo memberCard) {
        this.amount = amount;
        this.year = year;
        this.month = month;
        this.memberCard = memberCard;
        this.lastFinancialId = 0;
    }

    public static MemberCardPerformance empty(Integer year, Integer month, MemberCardInfo memberCardInfo) {
        return new MemberCardPerformance(0, year, month, memberCardInfo);
    }

    public void setLastFinancialId(Integer lastFinancialId, Integer amount) {
        this.lastFinancialId = lastFinancialId;
        this.amount = amount;
    }
}
