package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.ExpenseShare;
import com.expenses.splitwise.entity.ExpenseUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, ExpenseUserId> {
    List<ExpenseShare> findByExpenseName(String expenseName);
    @Query("SELECT es.percentage FROM ExpenseShare es WHERE es.expenseName = :expenseName AND es.userName = :userName")
    Optional<BigDecimal> findPercentageByExpenseNameAndUserName(@Param("expenseName") String expenseName, @Param("userName") String userName);
    @Query("SELECT es.amount FROM ExpenseShare es WHERE es.expenseName = :expenseName AND es.userName = :userName")
    Optional<BigDecimal> findAmountByExpenseNameAndUserName(String expenseName, String userName);
}
