package com.expenses.splitwise.dto;

/**
 * Enum representing different types of splits for expenses.
 * <p>
 * Available split types:
 * - EQUAL: Splits the expense equally among all participants
 * - PERCENTAGE: Splits based on specified percentage per user
 * - MANUAL: Allows manual entry of share amounts
 * </p>
 *
 * @see ExpenseDto
 * @see ExpenseShareDto
 */
public enum SPLIT_TYPE {
    /**
     * Divides the expense amount equally among all participants
     */
    EQUAL,

    /**
     * Splits the expense based on specified percentages for each user
     */
    PERCENTAGE,

    /**
     * Allows manual entry of specific share amounts for each user
     */
    MANUAL
}
