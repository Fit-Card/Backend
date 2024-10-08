package com.fitcard.domain.member.model;

import com.fitcard.domain.member.model.dto.request.MemberRegisterRequest;
import com.fitcard.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @NotNull
    private LocalDate birthDate;

    @NotEmpty
    private String phoneNumber;

    @NotNull
    private Boolean isCertifiedMydata;

    private String userSeqNo;

    private Member(String loginId, String password, String name, LocalDate birthDate, String phoneNumber, Boolean isCertifiedMydata, String userSeqNo) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.isCertifiedMydata = isCertifiedMydata;
        this.userSeqNo = userSeqNo;
    }

    public static Member of(MemberRegisterRequest request, String encodedPassword) {
        return new Member(
                request.getLoginId(),
                encodedPassword,  // 인코딩된 패스워드를 사용
                request.getName(),
                request.getBirthDate(),
                request.getPhoneNumber(),
                true,   // isCertifiedMydata 초기값
                ""       // userSeqNo 초기값
        );
    }

    public static Member of(String loginId, String password, String name, LocalDate birthDate, String phoneNumber, Boolean isCertifiedMydata, String userSeqNo) {
        return new Member(loginId, password, name, birthDate, phoneNumber, isCertifiedMydata, userSeqNo);
    }

    public void update(String password) {
        this.password = password;
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        this.phoneNumber = newPhoneNumber;
    }
}
