package com.expenses.splitwise.service;

import com.expenses.splitwise.dto.ExpenseShareDto;
import com.expenses.splitwise.dto.UserDto;
import com.expenses.splitwise.entity.*;
import com.expenses.splitwise.repository.ExpenseShareRepository;
import com.expenses.splitwise.repository.ExpenseUserRepository;
import com.expenses.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final ExpenseUserRepository expenseUserRepository;
    private final ExpenseShareRepository expenseShareRepository;

    public UserService(UserRepository userRepository, ExpenseUserRepository expenseUserRepository, ExpenseShareRepository expenseShareRepository) {
        this.userRepository = userRepository;
        this.expenseUserRepository = expenseUserRepository;
        this.expenseShareRepository = expenseShareRepository;
    }

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

    private UserDto convertToDto(User user) {
        return new UserDto(user.getName());
    }

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