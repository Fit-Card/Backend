package com.financial.domain.organ.model;

import com.financial.global.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "organ")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Organ extends BaseEntity {

    @Id
    private String id;

    @NotBlank
    private String name;
}
