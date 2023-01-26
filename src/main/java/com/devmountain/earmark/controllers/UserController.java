package com.devmountain.earmark.controllers;

import com.devmountain.earmark.dtos.IncomeDTO;
import com.devmountain.earmark.dtos.UpdateIncomeDto;
import com.devmountain.earmark.dtos.UserDto;
import com.devmountain.earmark.dtos.UserResponse;
import com.devmountain.earmark.entities.BudgetItem;
import com.devmountain.earmark.exceptions.UserNotFoundException;
import com.devmountain.earmark.services.UserService;
import org.h2.jdbc.JdbcException;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public List<String> addUser(@RequestBody UserDto userDto) throws Exception {
            return userService.addUser(userDto);
    }


    @PostMapping("/income")
    public List<String> addIncome(@RequestBody IncomeDTO incomeDTO) throws UserNotFoundException {
        return userService.addIncome(incomeDTO);
    }

    @GetMapping("/budget/{id}")
    public List<BudgetItem> getUserBudget(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserBudget(id);
    }

    @PostMapping("login")
    public List<String> userLogin(@RequestBody UserDto userDto) {
        return userService.userLogin(userDto);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @PutMapping("income/update")
    public List<String> updateIncome(@RequestBody UpdateIncomeDto incomeDTO) throws UserNotFoundException {
        return userService.updateIncome(incomeDTO);
    }

    @DeleteMapping("/income/{userId}/{id}")
    public List<String> deleteIncome(@PathVariable Long userId, @PathVariable Long id) throws UserNotFoundException {
        return userService.deleteIncome(userId, id);
    }

    @DeleteMapping("/all")
    public List<String> deleteAllUsers() {
        return userService.deleteAllUsers();
    }

}
