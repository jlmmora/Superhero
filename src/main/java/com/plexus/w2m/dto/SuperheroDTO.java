package com.plexus.w2m.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SuperheroDTO {

    private Long id;
    @NotEmpty
    @NotNull
    private String name;
    private String realName;

    @NotNull
    @NotEmpty
    private String abilities;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private double power;

}