package com.fitcard.domain.member.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
public class Member {

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

    public static Member of(String loginId, String password, String name, LocalDate birthDate, String phoneNumber, Boolean isCertifiedMydata, String userSeqNo) {
        return new Member(loginId, password, name, birthDate, phoneNumber, isCertifiedMydata, userSeqNo);
    }

    public void update(String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
