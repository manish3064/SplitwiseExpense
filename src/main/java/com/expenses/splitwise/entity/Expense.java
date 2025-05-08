package com.expenses.splitwise.entity;

import com.expenses.splitwise.dto.SPLIT_TYPE;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity class representing an expense record in the system.
 * <p>
 * This class maps to the 'expenses' table and contains:
 * - Basic expense details (date, name, amount)
 * - Group association
 * - Split type configuration
 * - Creator reference
 * </p>
 *
 * @see User
 * @see SPLIT_TYPE
 */
@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    /**
     * Unique identifier for the expense
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Date when the expense was created or incurred
     */
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    /**
     * Name of the group this expense belongs to
     */
    @Column(name = "group_name", nullable = false, length = 100)
    private String groupName;

    /**
     * Unique identifier/name for the expense
     */
    @Column(name = "expense_name", nullable = false, unique = true, length = 100)
    private String expenseName;

    /**
     * Total amount of the expense
     */
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    /**
     * Type of split applied to this expense
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "split_type", nullable = false)
    private SPLIT_TYPE splitType;

    /**
     * Reference to the user who created the expense
     */
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "name", nullable = false)
    private User createdBy;
}
