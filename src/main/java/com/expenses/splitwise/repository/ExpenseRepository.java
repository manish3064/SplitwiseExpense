package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
@EnableJpaRepositories(basePackages = "com.expenses.splitwise.repository")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Expense findByExpenseName(String expenseName);
}