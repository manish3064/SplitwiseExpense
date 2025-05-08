package com.expenses.splitwise.service;

import java.util.List;

public interface ExpenseUserInterface {
    void createExpenseUserMapping(String expenseName, List<String> userNames);
}
