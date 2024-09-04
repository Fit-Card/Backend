package com.fitcard.domain.membercard.membercardinfo.model;

import com.fitcard.domain.card.version.model.CardVersion;
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
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion memberId;

    @ManyToOne
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion cardVersionId;

    @ManyToOne
    @JoinColumn(name = "card_version_id", nullable = false)
    private CardVersion cardCompanyId;

    private LocalDate expiredDate;

    @NotEmpty
    private String globalBrand;

    @NotNull
    private Boolean isPersonal;

    // private 생성자
    private MemberCardInfo(CardVersion memberId, CardVersion cardVersionId, CardVersion cardCompanyId, LocalDate expiredDate, String globalBrand, Boolean isPersonal) {
        this.memberId = memberId;
        this.cardVersionId = cardVersionId;
        this.cardCompanyId = cardCompanyId;
        this.expiredDate = expiredDate;
        this.globalBrand = globalBrand;
        this.isPersonal = isPersonal;
    }
}
