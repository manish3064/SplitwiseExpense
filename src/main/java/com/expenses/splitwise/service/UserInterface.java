package com.expenses.splitwise.service;

import com.expenses.splitwise.dto.ExpenseShareDto;
import com.expenses.splitwise.dto.UserDto;

import java.util.List;

public interface UserInterface {
    UserDto createUser(UserDto userDto);
    List<ExpenseShareDto> calculateUserExpenseShares(String userName);
}
