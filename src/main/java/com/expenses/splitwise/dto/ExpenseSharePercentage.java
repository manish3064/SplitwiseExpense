package com.expenses.splitwise.dto;

import lombok.*;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSharePercentage {
    private String userName;
    private BigDecimal percentage;
    private BigDecimal amount;
}
