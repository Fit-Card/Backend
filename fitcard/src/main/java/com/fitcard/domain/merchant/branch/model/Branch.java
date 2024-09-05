package com.fitcard.domain.merchant.branch.model;

import com.fitcard.domain.merchant.merchantinfo.model.MerchantInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String kakaoUrl;  // Optional field, not validated for emptiness

    // private 생성자
    private Branch(MerchantInfo merchantInfo, String address, String branchName, String kakaoUrl) {
        this.merchantInfo = merchantInfo;
        this.address = address;
        this.branchName = branchName;
        this.kakaoUrl = kakaoUrl;
    }
}
