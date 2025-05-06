package com.expenses.splitwise.entity;

import com.expenses.splitwise.dto.SPLIT_TYPE;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "group_name", nullable = false, length = 100)
    private String groupName;

    @Column(name = "expense_name", nullable = false, unique = true, length = 100)
    private String expenseName;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "split_type", nullable = false)
    private SPLIT_TYPE splitType;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "name", nullable = false)
    private User createdBy;
}