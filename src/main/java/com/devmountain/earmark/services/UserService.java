package com.devmountain.earmark.services;

import com.devmountain.earmark.dtos.*;
import com.devmountain.earmark.entities.AppUser;
import com.devmountain.earmark.entities.BudgetItem;
import com.devmountain.earmark.exceptions.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    @Transactional
    List<String> addUser(UserDto userDto) throws UserNotFoundException;

    @Transactional
    List<String> userLogin(UserDto userDto);

    @Transactional
    AppUser getUser(Long userId) throws UserNotFoundException;


    @Transactional
    void save(AppUser appUser);
    @Transactional
    List<UserResponse> getAllUsers();

    @Transactional
    List<String> deleteUser(Long id);

    @Transactional
    List<String> deleteAllUsers();

    @Transactional
    List<BudgetItem> getUserBudget(Long id) throws UserNotFoundException;
    @Transactional
    List<String> addIncome(IncomeDTO incomeDTO) throws UserNotFoundException;

    UserResponse getUserById(Long id) throws UserNotFoundException;

    List<String> updateIncome(UpdateIncomeDto incomeDTO) throws UserNotFoundException;

    List<String> deleteIncome(Long userId, Long id) throws UserNotFoundException;
}
