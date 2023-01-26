package com.devmountain.earmark.repositories;

import com.devmountain.earmark.entities.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetItemRepository extends JpaRepository<BudgetItem,Long> {
}
