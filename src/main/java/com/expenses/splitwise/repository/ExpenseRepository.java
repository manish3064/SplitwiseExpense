package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Expense entities.
 * <p>
 * This repository provides CRUD operations for expenses and custom query methods.
 * It extends JpaRepository to inherit standard data access operations.
 * </p>
 *
 * @see Expense
 * @see JpaRepository
 */
@Repository
@EnableJpaRepositories(basePackages = "com.expenses.splitwise.repository")
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}