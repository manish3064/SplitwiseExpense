package com.expenses.splitwise.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for Expense entities.
 * <p>
 * This class represents the expense data structure for API communication and contains:
 * - Expense date and name
 * - Group association
 * - Total amount information
 * - Split type configuration
 * - Creator information
 * </p>
 *
 * @see SPLIT_TYPE
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    /**
     * Date when the expense was created or incurred
     */
    private LocalDate expenseDate;

    /**
     * Name of the group this expense belongs to
     */
    private String groupName;

    /**
     * Unique identifier/name for the expense
     */
    private String expenseName;

    /**
     * Total amount of the expense
     */
    private BigDecimal totalAmount;

    /**
     * Type of split to be applied for this expense
     */
    private SPLIT_TYPE split_type;

    /**
     * Username of the person who created the expense
     */
    private String createdBy;
}
