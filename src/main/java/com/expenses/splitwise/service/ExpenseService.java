package com.expenses.splitwise.service;

import com.expenses.splitwise.dto.ExpenseDto;
import com.expenses.splitwise.entity.Expense;
import com.expenses.splitwise.entity.User;
import com.expenses.splitwise.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;


    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        User creator = userRepository.findByName(expenseDto.getCreatedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Expense expense = new Expense();
        expense.setExpenseDate(expenseDto.getExpenseDate());
        expense.setGroupName(expenseDto.getGroupName());
        expense.setExpenseName(expenseDto.getExpenseName());
        expense.setTotalAmount(expenseDto.getTotalAmount());
        expense.setSplitType(expenseDto.getSplit_type());
        expense.setCreatedBy(creator);

        Expense savedExpense = expenseRepository.save(expense);
        return convertToDto(savedExpense);
    }

    private ExpenseDto convertToDto(Expense expense) {
        return new ExpenseDto(
                expense.getExpenseDate(),
                expense.getGroupName(),
                expense.getExpenseName(),
                expense.getTotalAmount(),
                expense.getSplitType(),
                expense.getCreatedBy().getName()
        );
    }
}