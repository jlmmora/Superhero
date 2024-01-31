package com.plexus.w2m.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
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
    private String name;
    private String realName;

    @NotEmpty
    private String abilities;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private double power;

}