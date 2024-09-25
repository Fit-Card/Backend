package com.fitcard.domain.membercard.membercardinfo.model;

import com.fitcard.domain.card.version.model.CardVersion;
import com.fitcard.domain.member.model.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member_card")
@Getter
@NoArgsConstructor
public class MemberCardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCardId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @ManyToOne
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion cardVersion;

    private LocalDate expiredDate;

    @NotEmpty
    private String globalBrand;

    @NotNull
    private Boolean isPersonal;

    @NotNull
    private Long financialUserCardId;

    // private 생성자
    private MemberCardInfo(Member member, CardVersion cardVersion, LocalDate expiredDate, String globalBrand, Boolean isPersonal, Long financialUserCardId) {
        this.member = member;
        this.expiredDate = expiredDate;
        this.globalBrand = globalBrand;
        this.isPersonal = isPersonal;
        this.financialUserCardId = financialUserCardId;
    }

    public static MemberCardInfo of(Member member, CardVersion cardVersion, String globalBrand, String expiredDate, String cardMemberType, long financialUserCardId) {
        boolean isPersonal = cardMemberType.equals("P");
        return new MemberCardInfo(member, cardVersion, LocalDate.parse(expiredDate), globalBrand, isPersonal, financialUserCardId);

    }
}
