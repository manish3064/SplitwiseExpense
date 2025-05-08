package com.expenses.splitwise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object for User entities.
 * <p>
 * This class represents the essential user information for API communication:
 * - User's display name with validation constraints
 * </p>
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    /**
     * User's display name.
     * Must not be blank as enforced by validation constraint.
     */
    @NotBlank(message = "Name is required")
    private String name;
}
