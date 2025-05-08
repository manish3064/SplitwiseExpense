package com.expenses.splitwise.service.impl;

import com.expenses.splitwise.dto.ExpenseShareDto;
import com.expenses.splitwise.dto.UserDto;
import com.expenses.splitwise.entity.*;
import com.expenses.splitwise.repository.ExpenseShareRepository;
import com.expenses.splitwise.repository.ExpenseUserRepository;
import com.expenses.splitwise.repository.UserRepository;
import com.expenses.splitwise.service.UserInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing user-related operations and expense share calculations.
 * <p>
 * This service handles:
 * - User creation and management
 * - Expense share calculations for users
 * - Transaction direction determination (credit/debit)
 * - Integration with expense and share repositories
 * </p>
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserInterface {
    /** Repository for managing user entities */
    private final UserRepository userRepository;

    /** Repository for managing expense-user mapping entities */
    private final ExpenseUserRepository expenseUserRepository;

    /** Repository for managing expense share entities */
    private final ExpenseShareRepository expenseShareRepository;

    /**
     * Constructs a new UserServiceImpl with required repositories.
     *
     * @param userRepository repository for user operations
     * @param expenseUserRepository repository for expense-user mapping operations
     * @param expenseShareRepository repository for expense share operations
     */
    public UserServiceImpl(UserRepository userRepository, ExpenseUserRepository expenseUserRepository, ExpenseShareRepository expenseShareRepository) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.expenseShareRepository = expenseShareRepository;
    }

    /**
     * Creates a new user from the provided DTO.
     * <p>
     * Validates the user data and creates a new user entity in the system.
     * </p>
     *
     * @param userDto the user data transfer object containing user details
     * @return the created user as DTO
     * @throws IllegalArgumentException if user name is null or empty
     */
    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto == null || userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        User user = new User();
        user.setName(userDto.getName().trim());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    /**
     * Converts a User entity to its DTO representation.
     *
     * @param user the user entity to convert
     * @return the user DTO
     */
    private UserDto convertToDto(User user) {
        return new UserDto(user.getName());
    }

    /**
     * Calculates expense shares for a specific user.
     * <p>
     * This method:
     * 1. Retrieves all expense mappings for the user
     * 2. Calculates shares based on expense split type (EQUAL, PERCENTAGE, MANUAL)
     * 3. Determines transaction direction (credit/debit)
     * 4. Returns a list of expense share DTOs
     * </p>
     *
     * @param userName the name of the user to calculate shares for
     * @return list of expense share DTOs containing share calculations
     */
    @Override
    public List<ExpenseShareDto> calculateUserExpenseShares(String userName) {
        List<ExpenseUser> userMappings = expenseUserRepository.findByUserName(userName);

        return userMappings.stream().map(eu -> {
            Expense e = eu.getExpense();
            String expenseName = e.getExpenseName();

            BigDecimal share = BigDecimal.ZERO;

            switch (e.getSplitType()) {
                case EQUAL -> {
                    long userCount = expenseUserRepository.countUsersForExpense(expenseName);
                    BigDecimal perUserShare = e.getTotalAmount().divide(BigDecimal.valueOf(userCount), 2, RoundingMode.HALF_UP);
                    share = perUserShare;
                }
                case PERCENTAGE -> {
                    Optional<BigDecimal> optionalPercentage = expenseShareRepository.findPercentageByExpenseNameAndUserName(expenseName, userName);
                    BigDecimal percentage = optionalPercentage.orElse(BigDecimal.ZERO); // or throw a custom exception if needed
                    share = e.getTotalAmount()
                            .multiply(percentage)
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                }
                case MANUAL -> {
                    Optional<BigDecimal> amount = expenseShareRepository.findAmountByExpenseNameAndUserName(expenseName, userName);
                    BigDecimal manualAmount = amount.orElse(BigDecimal.ZERO);
                    share = manualAmount;
                }
            }

            // Determine direction of transaction (credit or debit)
            if (e.getCreatedBy().getName().equals(userName)) {
                // Creator is credited what others owe
                long count = expenseUserRepository.countUsersForExpense(expenseName);
                share = e.getTotalAmount().subtract(share); // amount owed by others
            } else {
                // Others owe the calculated amount
                share = share.negate();
            }

            return new ExpenseShareDto(
                    e.getExpenseDate(),
                    e.getGroupName(),
                    e.getExpenseName(),
                    e.getTotalAmount(),
                    e.getCreatedBy().getName(),
                    e.getSplitType(),
                    share
            );
        }).collect(Collectors.toList());
    }

}