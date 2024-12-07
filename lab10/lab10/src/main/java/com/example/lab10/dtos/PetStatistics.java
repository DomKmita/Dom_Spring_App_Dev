package com.example.lab10.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetStatistics {
    private double averageAge;
    private long totalCount;
}
