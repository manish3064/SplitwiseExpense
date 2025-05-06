package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories(basePackages = "com.expenses.splitwise.repository")
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByName(String name);
}
