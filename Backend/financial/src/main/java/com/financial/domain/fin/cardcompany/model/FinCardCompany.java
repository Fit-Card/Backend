package com.financial.domain.fin.cardcompany.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "fin_card_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FinCardCompany {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotEmpty
    private String image;
}
