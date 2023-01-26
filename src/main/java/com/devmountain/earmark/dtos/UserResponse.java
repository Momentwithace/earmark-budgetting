package com.devmountain.earmark.dtos;

import com.devmountain.earmark.entities.BudgetItem;
import com.devmountain.earmark.entities.Income;
import com.devmountain.earmark.entities.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {
    private Long id;
    private String username;
    private List<BudgetItem> budgetItems;
    private Set<Income> incomeHistory;
    private Set<Log> logs;
    private BasicStatDto basicStat;
}
