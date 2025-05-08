package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.ExpenseShare;
import com.expenses.splitwise.entity.ExpenseUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing ExpenseShare entities.
 * <p>
 * This repository provides:
 * - CRUD operations for expense shares
 * - Custom queries for finding shares by expense and user
 * - Methods to retrieve percentage and amount calculations
 * </p>
 *
 * @see ExpenseShare
 * @see ExpenseUserId
 * @see JpaRepository
 */
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, ExpenseUserId> {

    /**
     * Retrieves the percentage share for a specific expense and user combination.
     *
     * @param expenseName the name of the expense
     * @param userName the name of the user
     * @return an Optional containing the percentage if found
     */
    @Query("SELECT es.percentage FROM ExpenseShare es WHERE es.expenseName = :expenseName AND es.userName = :userName")
    Optional<BigDecimal> findPercentageByExpenseNameAndUserName(@Param("expenseName") String expenseName, @Param("userName") String userName);

    /**
     * Retrieves the amount share for a specific expense and user combination.
     *
     * @param expenseName the name of the expense
     * @param userName the name of the user
     * @return an Optional containing the amount if found
     */
    @Query("SELECT es.amount FROM ExpenseShare es WHERE es.expenseName = :expenseName AND es.userName = :userName")
    Optional<BigDecimal> findAmountByExpenseNameAndUserName(String expenseName, String userName);
}
