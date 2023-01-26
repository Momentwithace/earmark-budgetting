package com.devmountain.earmark.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicStatDto {
   private Double lowestIncome, highestIncome, averageBudget, highestBudget,lowestBudget, averageIncome, income, budgetTotal;
}
