package com.expenses.splitwise.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private LocalDate expenseDate;
    private String groupName;
    private String expenseName;
    private BigDecimal totalAmount;
    private SPLIT_TYPE split_type;
    private String createdBy;

}

