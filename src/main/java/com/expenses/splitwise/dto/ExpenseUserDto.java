package com.expenses.splitwise.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseUserDto {
    private String expenseName;
    private String userName;
}
