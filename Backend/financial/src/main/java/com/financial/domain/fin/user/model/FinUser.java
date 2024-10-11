package com.financial.domain.fin.user.model;

import com.financial.domain.fin.user.model.dto.request.FinUserSaveRequest;
import com.financial.global.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@Entity
@Table(name = "fin_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinUser extends BaseEntity {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    @Id
    private String id;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private boolean isAuthentication;

    private FinUser(String phoneNumber) {
        this.id = LocalDateTime.now().format(formatter)+String.format("%06d", (int)(Math.random() * 1000000));
        this.phoneNumber = phoneNumber;
        this.isAuthentication = false;
    }

    public static FinUser from(FinUserSaveRequest request) {
        return new FinUser(request.getPhoneNumber());
    }
}
