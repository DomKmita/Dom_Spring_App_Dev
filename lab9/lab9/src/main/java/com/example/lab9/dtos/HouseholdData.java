package com.example.lab9.dtos;

import jakarta.validation.constraints.*;


public record HouseholdData(
        @NotEmpty @NotNull String eircode,
        @Min(0) @Max(20) int numberOfOccupants,
        @Min(0) @Max(20) int maxNumberOfOccupants,
        @NotNull boolean ownerOccupied
) {}
