package com.fitcard.domain.membercard.payment.model;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_card_payment")
@Getter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCardPaymentId;

    @ManyToOne
    @JoinColumn(name = "member_card_id", nullable = false)
    private MemberCardInfo memberCard;

    @NotNull
    private Integer amount;

    @NotNull
    private LocalDateTime paymentDate;

    @NotEmpty
    private String paymentName;

    @NotEmpty
    private String paymentCategory;

    @NotNull
    private Integer financialMemberCardPaymentId;

    public Payment(MemberCardInfo memberCard, Integer amount, LocalDateTime paymentDate, String paymentName, String paymentCategory, Integer financialMemberCardPaymentId) {
        this.memberCard = memberCard;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentName = paymentName;
        this.paymentCategory = paymentCategory;
        this.financialMemberCardPaymentId = financialMemberCardPaymentId;
    }

    public static Payment of(MemberCardInfo memberCardInfo, Integer financialMemberCardPaymentId, Integer amount, LocalDateTime paymentDate, String paymentName, String paymentCategory) {
        return new Payment(memberCardInfo, amount, paymentDate, paymentName, paymentCategory, financialMemberCardPaymentId);
    }
}
