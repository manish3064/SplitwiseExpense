package com.expenses.splitwise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity class representing a user's share in an expense.
 * <p>
 * This class maps to the 'expense_share' table and manages:
 * - The connection between an expense and a user
 * - Share details (percentage and amount)
 * - Bidirectional relationships with Expense and User entities
 * </p>
 *
 * @see Expense
 * @see User
 */
@Entity
@Table(name = "expense_share")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseShare {
    /**
     * Unique identifier for the expense share
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the expense this share belongs to
     */
    @Column(name = "expense_name")
    private String expenseName;

    /**
     * Username of the person sharing the expense
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * Percentage of the total expense allocated to this user
     */
    @Column(precision = 5, scale = 2)
    private BigDecimal percentage;

    /**
     * Actual amount to be paid by the user
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

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
