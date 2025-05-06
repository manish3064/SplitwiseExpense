package com.expenses.splitwise.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "expense_share")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_name")
    private String expenseName;

    @Column(name = "user_name")
    private String userName;

    @Column(precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "expense_name", referencedColumnName = "expense_name", insertable = false, updatable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "name", insertable = false, updatable = false)
    private User user;   // for MANUAL split
}
