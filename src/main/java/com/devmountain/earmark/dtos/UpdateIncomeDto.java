package com.devmountain.earmark.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIncomeDto {
   private Long userId;
   private Long incomeId;
   private Double newIncome;
}
