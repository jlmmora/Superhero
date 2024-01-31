package com.plexus.w2m.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="superheroes")
public class Superhero {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    @NotNull
    private String name;
    @Column(name = "real_name")
    private String realName;

    @NotEmpty
    @NotNull
    @Column(name = "abilities", nullable = false)
    private String abilities;
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(name = "power", nullable = false)
    private double power;


}