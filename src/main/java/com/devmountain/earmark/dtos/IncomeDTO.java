package com.devmountain.earmark.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {
    private Double income;
    private Long userId;
}
