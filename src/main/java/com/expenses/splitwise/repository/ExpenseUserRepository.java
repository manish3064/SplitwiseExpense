package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.ExpenseUser;
import com.expenses.splitwise.entity.ExpenseUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@EnableJpaRepositories(basePackages = "com.expenses.splitwise.repository")
public interface ExpenseUserRepository extends JpaRepository<ExpenseUser, ExpenseUserId> {
    List<ExpenseUser> findByUserName(String userName);
    @Query("SELECT COUNT(eu) FROM ExpenseUser eu WHERE eu.expenseName = :expenseName")
    long countUsersForExpense(@Param("expenseName") String expenseName);
}