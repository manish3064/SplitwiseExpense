package com.expenses.splitwise.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Composite key class for the ExpenseUser entity.
 * <p>
 * This class implements Serializable and serves as the composite primary key for
 * the many-to-many relationship between Expense and User entities. It consists of:
 * - Expense name as the first part of the key
 * - User name as the second part of the key
 * </p>
 *
 * @see ExpenseUser
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseUserId implements Serializable {
    /**
     * Expense name component of the composite key
     */
    private String expenseName;

    /**
     * Username component of the composite key
     */
    private String userName;
}
