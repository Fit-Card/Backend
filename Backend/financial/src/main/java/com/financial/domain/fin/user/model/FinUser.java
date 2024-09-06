package com.financial.domain.fin.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "fin_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinUser {

    @Id
    private String id;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private boolean isAuthentication;
}