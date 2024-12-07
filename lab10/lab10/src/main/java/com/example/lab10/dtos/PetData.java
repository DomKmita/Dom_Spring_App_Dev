package com.example.lab10.dtos;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PetData(
        @NotEmpty @NotNull String name,
        @NotEmpty @NotNull String animalType,
        @NotEmpty @NotNull String breed,
        @Min(0) @Max(20) int age,
        @NotEmpty @NotNull String eircode
) {}