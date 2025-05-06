package com.expenses.splitwise.service;

import com.expenses.splitwise.entity.ExpenseUser;
import com.expenses.splitwise.repository.ExpenseUserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExpenseUserService {
    private final ExpenseUserRepository expenseUserRepository;

    public ExpenseUserService(ExpenseUserRepository expenseUserRepository) {
        this.expenseUserRepository = expenseUserRepository;
    }

    public void createExpenseUserMapping(String expenseName, List<String> userNames) {
        userNames.forEach(userName -> {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setExpenseName(expenseName);
            expenseUser.setUserName(userName);
            expenseUserRepository.save(expenseUser);
        });
    }
}