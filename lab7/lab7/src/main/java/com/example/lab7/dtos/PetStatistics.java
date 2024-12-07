package com.example.lab7.dto;

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
