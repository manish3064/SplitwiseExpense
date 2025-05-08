package com.expenses.splitwise.service.impl;

import com.expenses.splitwise.dto.ExpenseDto;
import com.expenses.splitwise.entity.Expense;
import com.expenses.splitwise.entity.User;
import com.expenses.splitwise.repository.*;
import com.expenses.splitwise.service.ExpenseInterface;
import org.springframework.stereotype.Service;

/**
 * Service class for managing expense-related operations.
 * <p>
 * This service handles the business logic for:
 * - Creating new expenses
 * - Converting between entities and DTOs
 * - Managing expense relationships with users
 * </p>
 */
@Service
public class ExpenseServiceImpl implements ExpenseInterface {

    /** Repository for managing expense entities */
    private final ExpenseRepository expenseRepository;

    /** Repository for managing user entities */
    private final UserRepository userRepository;

    /**
     * Constructs a new ExpenseServiceImpl with required repositories.
     *
     * @param expenseRepository repository for expense operations
     * @param userRepository repository for user operations
     */
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates a new expense from the provided DTO.
     * <p>
     * This method:
     * 1. Validates the creator exists
     * 2. Converts DTO to entity
     * 3. Saves the expense
     * 4. Returns the saved expense as DTO
     * </p>
     *
     * @param expenseDto the expense data transfer object containing expense details
     * @return the created expense as DTO
     * @throws RuntimeException if the creator user is not found
     */
    @Override
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

    /**
     * Converts an Expense entity to its DTO representation.
     *
     * @param expense the expense entity to convert
     * @return the expense DTO
     */
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