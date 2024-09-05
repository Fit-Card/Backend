package com.fitcard.domain.membercard.payment.model;

import com.fitcard.domain.membercard.membercardinfo.model.MemberCardInfo;
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
public class Payment {

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

    private Payment(MemberCardInfo memberCard, Integer amount, LocalDateTime paymentDate, String paymentName, String paymentCategory) {
        this.memberCard = memberCard;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentName = paymentName;
        this.paymentCategory = paymentCategory;
    }
}
