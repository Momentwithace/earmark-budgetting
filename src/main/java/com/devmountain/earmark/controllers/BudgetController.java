package com.devmountain.earmark.controllers;

import com.devmountain.earmark.dtos.BudgetItemDto;
import com.devmountain.earmark.dtos.UpdateItemDto;
import com.devmountain.earmark.exceptions.UserNotFoundException;
import com.devmountain.earmark.services.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @PostMapping("/addItem")
    public List<String> addItemToUserBudget(@RequestBody BudgetItemDto budgetItemDto) throws UserNotFoundException {
        return budgetService.addItemToUserBudget(budgetItemDto);
    }

    @PutMapping("/updateItem")
    public List<String> updateItemInUserBudget(@RequestBody UpdateItemDto updateItemDto) throws UserNotFoundException {
        return budgetService.updateItemInUserBudget(updateItemDto);
    }

    @DeleteMapping("/all")
    public List<String> deleteAllBudgetItems() {
        return budgetService.deleteAll();
    }

    @DeleteMapping("/{userId}/{id}")
    public List<String> deleteBudgetItem(@PathVariable Long userId, @PathVariable Long id) throws UserNotFoundException {
        return budgetService.deleteBudgetItem(userId, id);
    }
}
