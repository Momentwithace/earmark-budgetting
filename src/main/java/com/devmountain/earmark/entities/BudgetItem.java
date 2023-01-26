package com.devmountain.earmark.entities;

import com.devmountain.earmark.dtos.BudgetItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class BudgetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private Double price;


    public BudgetItem(BudgetItemDto budgetItemDto){
        if(budgetItemDto.getItem() != null){
            this.item = budgetItemDto.getItem();
        }
        if(budgetItemDto.getPrice() != null){
            this.price = budgetItemDto.getPrice();
        }

    }
}
