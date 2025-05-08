package com.expenses.splitwise.service.impl;

import com.expenses.splitwise.entity.ExpenseUser;
import com.expenses.splitwise.repository.ExpenseUserRepository;
import com.expenses.splitwise.service.ExpenseUserInterface;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for managing expense-user mapping operations.
 * <p>
 * This service handles the business logic for:
 * - Creating mappings between expenses and users
 * - Managing expense participation relationships
 * - Storing expense-user associations in the database
 * </p>
 *
 * @see ExpenseUser
 * @see ExpenseUserRepository
 */
@Service
public class ExpenseUserServiceImpl implements ExpenseUserInterface {

    /** Repository for managing expense-user mapping entities */
    private final ExpenseUserRepository expenseUserRepository;

    /**
     * Constructs a new ExpenseUserServiceImpl with required repository.
     *
     * @param expenseUserRepository repository for expense-user mapping operations
     */
    public ExpenseUserServiceImpl(ExpenseUserRepository expenseUserRepository) {
        this.expenseUserRepository = expenseUserRepository;
    }

    /**
     * Creates mappings between an expense and multiple users.
     * <p>
     * This method:
     * 1. Takes an expense name and a list of usernames
     * 2. Creates ExpenseUser entities for each user
     * 3. Saves the mappings to the database
     * </p>
     *
     * @param expenseName the name of the expense to map users to
     * @param userNames list of usernames to be mapped to the expense
     * @throws RuntimeException if there's an error during the mapping process
     */
    @Override
    public void createExpenseUserMapping(String expenseName, List<String> userNames) {
        userNames.forEach(userName -> {
            ExpenseUser expenseUser = new ExpenseUser();
            expenseUser.setExpenseName(expenseName);
            expenseUser.setUserName(userName);
            expenseUserRepository.save(expenseUser);
        });
    }
}
