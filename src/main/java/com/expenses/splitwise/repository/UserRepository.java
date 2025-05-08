package com.expenses.splitwise.repository;

import com.expenses.splitwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * <p>
 * This repository provides:
 * - CRUD operations for user management
 * - Custom query method for finding users by name
 * - String-based primary key operations
 * </p>
 *
 * @see User
 * @see JpaRepository
 */
@Repository
@EnableJpaRepositories(basePackages = "com.expenses.splitwise.repository")
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a user by their unique name.
     * <p>
     * This method returns an Optional to handle cases where the user may not exist.
     * </p>
     *
     * @param name the unique name of the user to find
     * @return an Optional containing the user if found, empty otherwise
     */
    Optional<User> findByName(String name);
}
