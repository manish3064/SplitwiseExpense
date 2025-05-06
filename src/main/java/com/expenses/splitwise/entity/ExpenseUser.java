package com.expenses.splitwise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ExpenseUserId.class)  // Composite key class
public class ExpenseUser {
    @Id
    @Column(name = "expense_name")
    private String expenseName;

    @Id
    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "expense_name", referencedColumnName = "expense_name", insertable = false, updatable = false)
    private Expense expense;

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "name", insertable = false, updatable = false)
    private User user;
}