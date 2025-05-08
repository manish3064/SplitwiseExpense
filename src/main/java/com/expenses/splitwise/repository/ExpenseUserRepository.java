package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.ExpenseUser;
import com.expenses.splitwise.entity.ExpenseUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for managing ExpenseUser entities.
 * <p>
 * This repository handles:
 * - CRUD operations for expense-user mappings
 * - Custom queries for user expense associations
 * - User count calculations for expenses
 * </p>
 *
 * @see ExpenseUser
 * @see ExpenseUserId
 * @see JpaRepository
 */
@Repository
@EnableJpaRepositories(basePackages = "com.expenses.splitwise.repository")
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, ExpenseUserId> {

    /**
     * Retrieves all expense mappings for a specific user.
     *
     * @param userName the name of the user to find mappings for
     * @return list of expense-user mappings for the given user
     */
    List<ExpenseUser> findByUserName(String userName);

    /**
     * Counts the number of users associated with a specific expense.
     *
     * @param expenseName the name of the expense to count users for
     * @return the number of users mapped to the expense
     */
    @Query("SELECT COUNT(eu) FROM ExpenseUser eu WHERE eu.expenseName = :expenseName")
    long countUsersForExpense(@Param("expenseName") String expenseName);
}
