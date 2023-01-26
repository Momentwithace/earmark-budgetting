package com.devmountain.earmark.services;

import com.devmountain.earmark.dtos.*;
import com.devmountain.earmark.entities.AppUser;
import com.devmountain.earmark.entities.BudgetItem;
import com.devmountain.earmark.entities.Income;
import com.devmountain.earmark.entities.Log;
import com.devmountain.earmark.exceptions.UserNotFoundException;
import com.devmountain.earmark.repositories.IncomeRepository;
import com.devmountain.earmark.repositories.LogRepository;
import com.devmountain.earmark.repositories.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<String> addUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if (EmailValidator.getInstance().isValid(userDto.getEmail())) {
            if (userRepository.existsByEmail(userDto.getEmail())) {
                return Collections.singletonList("Email already exists");
            }
            if (userRepository.existsByUsername(userDto.getUsername())) {
                return Collections.singletonList("Username already exists");
            }
            AppUser appUser = new AppUser(userDto);
            appUser.setLogs(Set.of(new Log("register")));
            appUser.setIncomeHistory(new HashSet<>());
            appUser.setBudgetItems(new ArrayList<>());
            userRepository.saveAndFlush(appUser);

            return List.of("success", "http://localhost:8080/login.html");
        }
        return Collections.singletonList("Invalid email");
    }

    @Override
    @Transactional
    public List<String> userLogin(UserDto userDto) {
        Optional<AppUser> userOptional = userRepository.findByUsername(userDto.getUsername());
        if (userOptional.isPresent()) {
            if (passwordEncoder.matches(userDto.getPassword(), userOptional.get().getPassword())) {
                userOptional.get().getLogs().add(new Log("login"));
                userRepository.save(userOptional.get());
                return List.of("http://localhost:8080/dashboard.html", String.valueOf(userOptional.get().getId()));
            }
        }
        return List.of("Invalid Username or password", "http://localhost:8080/login.html");
    }

    @Override
    @Transactional
    public AppUser getUser(Long id) throws UserNotFoundException {
        Optional<AppUser> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("user with id " + id + " not found");
        }
        return userOptional.get();
    }

    @Override
    public void save(AppUser appUser) {
        userRepository.saveAndFlush(appUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<AppUser> appUsers = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        appUsers.stream().map(user -> new UserResponse(user.getId(), user.getUsername(),
                user.getBudgetItems(), user.getIncomeHistory(), user.getLogs(), getUserStat(user))).forEach(userResponses::add);
        return userResponses;
    }

    private BasicStatDto getUserStat(AppUser user) {
        return BasicStatDto.builder()
                .averageIncome( Math.floor(user.getIncomeHistory().stream().mapToDouble(Income::getIncome).average().orElse(0)))
                .lowestIncome( user.getIncomeHistory().stream().mapToDouble(Income::getIncome).min().orElse(0))
                .highestIncome( user.getIncomeHistory().stream().mapToDouble(Income::getIncome).max().orElse(0))
                .averageBudget( Math.floor(user.getBudgetItems().stream().mapToDouble(BudgetItem::getPrice).average().orElse(0)))
                .lowestBudget( user.getBudgetItems().stream().mapToDouble(BudgetItem::getPrice).min().orElse(0))
                .highestBudget( user.getBudgetItems().stream().mapToDouble(BudgetItem::getPrice).max().orElse(0))
                .income( user.getIncomeHistory().stream().mapToDouble(Income::getIncome).sum())
                .budgetTotal( user.getBudgetItems().stream().mapToDouble(BudgetItem::getPrice).sum())
                .build();
    }

    @Override
    public List<String> deleteUser(Long id) {
        userRepository.deleteById(id);
        return Collections.singletonList("user deleted");
    }

    @Override
    public List<String> deleteAllUsers() {
        userRepository.deleteAll();
        incomeRepository.deleteAll();
        logRepository.deleteAll();
        return Collections.singletonList("all users deleted");
    }

    @Transactional
    @Override
    public List<BudgetItem> getUserBudget(Long id) throws UserNotFoundException {
        return getUser(id).getBudgetItems();
    }

    @Override
    @Transactional
    public List<String> addIncome(IncomeDTO incomeDTO) throws UserNotFoundException {
        AppUser user = getUser(incomeDTO.getUserId());
        user.getLogs().add(new Log("added income"));
        var income = new Income(incomeDTO.getIncome());
        income = incomeRepository.save(income);
        user.getIncomeHistory().add(income);
        userRepository.save(user);
        return Collections.singletonList("income added successfully");
    }

    @Override
    public UserResponse getUserById(Long id) throws UserNotFoundException {
        AppUser user = getUser(id);

        return new UserResponse(user.getId(), user.getUsername(), user.getBudgetItems(),
                user.getIncomeHistory(), checkLogs(user.getLogs()), getUserStat(user));
    }


    private Set<Log> checkLogs(Set<Log> logs) {
        if (logs.size() > 10) {
            return new HashSet<>(logs.stream().skip(logs.size() - 10).toList());
        }
        return logs;
    }

    @Override
    public List<String> updateIncome(UpdateIncomeDto incomeDTO) throws UserNotFoundException {
        AppUser user = getUser(incomeDTO.getUserId());
        user.getLogs().add(new Log("updated income"));
        user.getIncomeHistory().stream().filter(income -> income.getId().equals(incomeDTO.getIncomeId()))
                .findFirst()
                .ifPresent(income -> {
                    if (incomeDTO.getNewIncome() > 0) {
                        income.setIncome(incomeDTO.getNewIncome());
                    }
                });
        userRepository.save(user);
        return Collections.singletonList("income updated successfully");
    }

    @Override
    public List<String> deleteIncome(Long userId, Long id) throws UserNotFoundException {
        AppUser user = getUser(userId);
        user.getLogs().add(new Log("deleted income"));
        user.getIncomeHistory().removeIf(income -> income.getId().equals(id));
        userRepository.save(user);
        return Collections.singletonList("income deleted successfully");
    }
}
