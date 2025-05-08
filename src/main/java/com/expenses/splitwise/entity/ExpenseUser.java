package com.expenses.splitwise.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing the many-to-many relationship between expenses and users.
 * <p>
 * This class maps to the 'expense_user' table and manages:
 * - The association between an expense and its participants
 * - Uses composite primary key through {@link ExpenseUserId}
 * - Bidirectional relationships with Expense and User entities
 * </p>
 *
 * @see Expense
 * @see User
 * @see ExpenseUserId
 */
@Entity
@Table(name = "expense_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ExpenseUserId.class)
public class ExpenseUser {
    /**
     * Part of composite primary key - expense identifier
     */
    @Id
    @Column(name = "expense_name")
    private String expenseName;

    /**
     * Part of composite primary key - user identifier
     */
    @Id
    @Column(name = "user_name")
    private String userName;

    /**
     * Bidirectional relationship to the expense
     */
    @ManyToOne
    @JoinColumn(name = "expense_name", referencedColumnName = "expense_name", insertable = false, updatable = false)
    private Expense expense;

    /**
     * Bidirectional relationship to the user
     */
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "name", insertable = false, updatable = false)
    private User user;
}
