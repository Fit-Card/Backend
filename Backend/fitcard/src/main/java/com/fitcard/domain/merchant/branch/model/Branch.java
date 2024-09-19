package com.fitcard.domain.merchant.branch.model;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import com.fitcard.infra.kakao.model.LocalInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Entity
@Table(name = "merchant_branch")
@Getter
@NoArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long merchantBranchId;

    @ManyToOne
    @JoinColumn(name = "merchant_id", nullable = false)
    private MerchantInfo merchantInfo;

    @NotEmpty
    private String address;

    @NotEmpty
    private String branchName;

    @NotEmpty
    private String x;

    @NotEmpty
    private String y;

    private String kakaoUrl;

    @NotEmpty
    private String kakaoLocalId;

    // private 생성자
    private Branch(MerchantInfo merchantInfo, String address, String branchName, String kakaoUrl, String x, String y, String kakaoLocalId) {
        this.merchantInfo = merchantInfo;
        this.address = address;
        this.branchName = branchName;
        this.kakaoUrl = kakaoUrl;
        this.x = x;
        this.y = y;
        this.kakaoLocalId = kakaoLocalId;
    }

    public static Branch of(LocalInfo localInfo, MerchantInfo merchantInfo){
        return new Branch(merchantInfo, localInfo.getRoadAddressName(), localInfo.getPlaceName(),
                localInfo.getPlaceUrl(), localInfo.getX(), localInfo.getY(), localInfo.getPlaceId());
    }
}
