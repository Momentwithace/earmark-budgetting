package com.devmountain.earmark.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetItemDto implements Serializable {
    private Long id;
    private String item;
    private Double price;
    private Long userId;


}
