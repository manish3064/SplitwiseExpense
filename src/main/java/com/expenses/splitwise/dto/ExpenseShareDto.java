package com.expenses.splitwise.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object representing an individual share of an expense.
 * <p>
 * This class extends the basic expense information with share-specific data:
 * - All base expense properties (date, group, name, amount)
 * - Share amount for a specific user
 * - Split type information
 * </p>
 *
 * @see ExpenseDto
 * @see SPLIT_TYPE
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseShareDto {
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
     * Total amount of the original expense
     */
    private BigDecimal totalAmount;

    /**
     * Username of the person who created the expense
     */
    private String createdBy;

    /**
     * Type of split applied to this expense
     */
    private SPLIT_TYPE split_type;

    /**
     * Individual share amount for a specific user
     */
    private BigDecimal share;
}
