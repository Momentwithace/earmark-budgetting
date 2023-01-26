package com.devmountain.earmark.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemDto {
    private Long userId;
    private Long budgetItemId;
    private String newItemName;
    private Double newItemPrice;
}
