package com.expenses.splitwise.service;

import com.expenses.splitwise.dto.ExpenseDto;

public interface ExpenseInterface {
    ExpenseDto createExpense(ExpenseDto expenseDto);
}
