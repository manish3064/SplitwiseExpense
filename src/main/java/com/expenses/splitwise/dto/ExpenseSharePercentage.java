package com.expenses.splitwise.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * Represents the share percentage of an expense for a user.
 * This class contains the user's name, the percentage of the expense they are responsible for,
 * and the amount that corresponds to that percentage.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSharePercentage {
    /**
     * The name of the user responsible for the expense share.
     */
    private String userName;
    /**
     * The percentage of the expense that the user is responsible for.
     */
    private BigDecimal percentage;
    /**
     * The amount that corresponds to the user's share of the expense.
     */
    private BigDecimal amount;
}
