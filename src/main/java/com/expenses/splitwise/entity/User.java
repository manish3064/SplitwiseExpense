package com.expenses.splitwise.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity class representing a user in the system.
 * <p>
 * This class maps to the 'users' table and contains:
 * - Basic user identification (id, name)
 * - Unique constraint on the user's name
 * </p>
 *
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /**
     * Unique identifier for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique name identifier for the user
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}