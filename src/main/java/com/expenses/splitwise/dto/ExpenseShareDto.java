package com.expenses.splitwise.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseShareDto {
    private LocalDate expenseDate;
    private String groupName;
    private String expenseName;
    private BigDecimal totalAmount;
    private String createdBy;
    private SPLIT_TYPE split_type;
    private BigDecimal share;
}