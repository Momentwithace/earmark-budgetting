package com.devmountain.earmark.services;

import com.devmountain.earmark.dtos.BudgetItemDto;
import com.devmountain.earmark.dtos.UpdateItemDto;
import com.devmountain.earmark.entities.BudgetItem;
import com.devmountain.earmark.exceptions.BudgetNotfoundException;
import com.devmountain.earmark.exceptions.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

public interface BudgetService {

    @Transactional
    List<String> addItemToUserBudget(BudgetItemDto budgetItemDto) throws UserNotFoundException;


    @Transactional
    List<String> deleteAll();
    @Transactional
    List<String> deleteBudgetItem(Long userId,Long id) throws UserNotFoundException;
    @Transactional
    List<String> updateItemInUserBudget(UpdateItemDto updateItemDto) throws UserNotFoundException;
}
