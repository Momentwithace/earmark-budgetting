package com.devmountain.earmark.services;

import com.devmountain.earmark.dtos.BudgetItemDto;
import com.devmountain.earmark.dtos.UpdateItemDto;
import com.devmountain.earmark.entities.AppUser;
import com.devmountain.earmark.entities.BudgetItem;
import com.devmountain.earmark.entities.Log;
import com.devmountain.earmark.exceptions.UserNotFoundException;
import com.devmountain.earmark.repositories.BudgetItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class BudgetServiceImpl implements BudgetService {
    @Autowired
    private BudgetItemRepository budgetItemRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<String> addItemToUserBudget(BudgetItemDto budgetItemDto) throws UserNotFoundException {
        AppUser appUser = userService.getUser(budgetItemDto.getUserId());
        appUser.getLogs().add(new Log("added budget"));
        BudgetItem budgetItem = new BudgetItem(budgetItemDto);
        budgetItemRepository.saveAndFlush(budgetItem);
        appUser.getBudgetItems().add(budgetItem);
        userService.save(appUser);
        return new ArrayList<>(List.of("Budget Item added successfully"));
    }


    @Override
    public List<String> deleteAll() {
        budgetItemRepository.deleteAll();
        return new ArrayList<>(List.of("all budget items deleted successfully"));
    }

    @Override
    public List<String> deleteBudgetItem(Long userId, Long budgetId) throws UserNotFoundException {
        AppUser appUser = userService.getUser(userId);
        appUser.getBudgetItems().removeIf(budgetItem -> budgetItem.getId().equals(budgetId));
        budgetItemRepository.deleteById(budgetId);
        userService.save(appUser);
        return new ArrayList<>(List.of("Budget Item deleted successfully"));
    }

    @Override
    public List<String> updateItemInUserBudget(UpdateItemDto updateItemDto) throws UserNotFoundException {
        AppUser appUser = userService.getUser(updateItemDto.getUserId());
        for (BudgetItem budgetItem : appUser.getBudgetItems()) {
            if (budgetItem.getId().equals(updateItemDto.getBudgetItemId())) {
                if(!updateItemDto.getNewItemName().isEmpty()){
                    budgetItem.setItem(updateItemDto.getNewItemName());
                }
                if(updateItemDto.getNewItemPrice() > 0){
                    budgetItem.setPrice(updateItemDto.getNewItemPrice());
                }
                appUser.getLogs().add(new Log("update budget"));
                userService.save(appUser);
                return new ArrayList<>(List.of("Budget Item updated successfully"));
            }
        }
        return new ArrayList<>(List.of("Budget Item not found"));
    }
}