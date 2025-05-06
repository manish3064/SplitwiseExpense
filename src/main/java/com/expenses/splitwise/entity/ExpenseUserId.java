package com.expenses.splitwise.entity;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseUserId implements Serializable {
    private String expenseName;
    private String userName;
}