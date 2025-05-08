package com.expenses.splitwise.controller;

import com.expenses.splitwise.dto.*;
import com.expenses.splitwise.entity.*;
import com.expenses.splitwise.repository.*;
import com.expenses.splitwise.service.ExpenseInterface;
import com.expenses.splitwise.service.ExpenseUserInterface;
import com.expenses.splitwise.service.UserInterface;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * REST controller for managing user-related and expense-related operations.
 * <p>
 * This controller provides endpoints for:
 * - User management (creation, retrieval)
 * - Expense management (creation, retrieval)
 * - Expense-user mappings
 * - Expense shares and calculations
 * </p>
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    /** Logger instance for this class */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /** Service for handling user-related operations */
    private final UserInterface userInterface;

    /** Service for handling expense-related operations */
    private final ExpenseInterface expenseInterface;

    /** Service for handling expense-user mapping operations */
    private final ExpenseUserInterface expenseUserInterface;

    /** Repository for managing expense shares */
    private final ExpenseShareRepository expenseShareRepository;

    /** Repository for managing users */
    private final UserRepository userRepository;

    /** Repository for managing expense-user mappings */
    private final ExpenseUserRepository expenseUserRepository;

    /** Repository for managing expenses */
    private final ExpenseRepository expenseRepository;

    /**
     * Creates a new user.
     * <p>
     * This endpoint validates and creates a new user in the system.
     * </p>
     *
     * @param userDto the user data transfer object containing user details
     * @return ResponseEntity containing success message or error details
     */
    @Operation(summary = "Create Users", description = "Saves the users record in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success message with user",content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Error message with list of errors",content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) })
    })
    @PostMapping(value = "/addUsers")
    public ResponseEntity<?> createUser(@Parameter @RequestBody UserDto userDto) {
        logger.info("Received request with userDto: {}", userDto);

        if (userDto.getName() == null || userDto.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Name cannot be null or empty");
        }

        try {
            UserDto createdUser = userInterface.createUser(userDto);
            logger.info("Created user: {}", createdUser);
            String message ="Successfully Created user: " + createdUser ;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Creates a new expense.
     * <p>
     * This endpoint validates and creates a new expense in the system.
     * </p>
     *
     * @param expenseDto the expense data transfer object containing expense details
     * @return ResponseEntity containing success message or error details
     */
    @Operation(summary = "Create Expenses", description = "Saves the expenses record in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success message with expenses",content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Error message with list of errors",content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) })
    })
    @PostMapping("/addExpenses")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseDto expenseDto) {
        log.info("Attempting to create expense: {}", expenseDto);

        try {
            ExpenseDto createdExpense = expenseInterface.createExpense(expenseDto);
            log.info("Successfully created expense: {}", createdExpense);
            String message ="Successfully created expense: " + createdExpense ;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            log.error("Failed to create expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create expense: " + e.getMessage());
        }
    }

    /**
     * Maps users to an expense.
     * <p>
     * This endpoint allows mapping multiple users to a specific expense.
     * </p>
     *
     * @param expenseName the name of the expense
     * @param request the list of user names to be mapped to the expense
     * @return ResponseEntity containing success message or error details
     */
    @Operation(summary = "Mapping Users", description = "Mapping Expenses to Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success message with mapping",content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Error message with list of errors",content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) })
    })
    @PostMapping("/expenses/{expenseName}/users")
    public ResponseEntity<?> addUsersToExpense(
            @PathVariable String expenseName,
            @RequestParam String request) {
        log.info("Adding users to expense: {}, users: {}", expenseName, request);
        try {
            List<String> userNames = List.of(request.split(","));
            expenseUserInterface.createExpenseUserMapping(expenseName, userNames);
            log.info("Successfully added users to expense: {}", expenseName);
            String message ="Successfully added users to expense: " + expenseName + " with users: " + request;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            log.error("Failed to add users to expense {}: {}", expenseName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add users to expense: " + e.getMessage());
        }

    }

    /**
     * Retrieves the expense shares for a specific user.
     * <p>
     * This endpoint calculates and returns the expense shares for a given user.
     * </p>
     *
     * @param userName the name of the user
     * @return ResponseEntity containing the list of expense shares or error details
     */
    @Operation(summary = "Get expense-shares", description = "Fetch the expense-shares record from the database by passing user name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Organisation response beans", content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Error message with list of errors",  content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Object.class)) })
    })
    @GetMapping("/users/{userName}/expense-shares")
    public ResponseEntity<?> getUserExpenseShares(@PathVariable String userName) {
        log.info("Retrieving expense shares for user: {}", userName);
        try {
            List<ExpenseShareDto> shares = userInterface.calculateUserExpenseShares(userName);
            log.info("Successfully retrieved {} expense shares for user: {}", shares.size(), userName);
            return ResponseEntity.ok(shares);
        } catch (Exception e) {
            log.error("Failed to retrieve expense shares for user {}: {}", userName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve expense shares: " + e.getMessage());
        }

    }

    /**
     * Adds shares for a specific expense.
     * <p>
     * This endpoint allows adding shares for a given expense.
     * </p>
     *
     * @param expenseName the name of the expense
     * @param shares the list of expense share percentages
     * @return ResponseEntity containing success message or error details
     */
    @Operation(summary = "Add Shares", description = "Adds shares for a specific expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success message with shares", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseShare.class))
            }),
            @ApiResponse(responseCode = "400", description = "Error message with list of errors", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json")
            })
    })
    @PostMapping("/{expenseName}")
    public ResponseEntity<?> addShares(
            @PathVariable String expenseName,
            @RequestBody List<ExpenseSharePercentage> shares) {

        log.info("Adding shares for expense: {}, count: {}", expenseName, shares.size());
        try {
            shares.forEach(shareDto -> {
                ExpenseShare share = new ExpenseShare();
                share.setExpenseName(expenseName);
                share.setUserName(shareDto.getUserName());
                share.setPercentage(shareDto.getPercentage());
                share.setAmount(shareDto.getAmount());
                expenseShareRepository.save(share);
            });
            log.info("Successfully added {} shares for expense: {}", shares.size(), expenseName);
            String message ="Successfully added "+ shares.size() + " shares for expense: " + expenseName;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            log.error("Failed to add shares for expense {}: {}", expenseName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add shares: " + e.getMessage());
        }

    }


    /**
     * Retrieves all users.
     * <p>
     * This endpoint fetches and returns a list of all users in the system.
     * </p>
     *
     * @return ResponseEntity containing the list of users
     */
    @GetMapping("/getUsers")
    public List<User> getUser() {
        List<User> userDto= userRepository.findAll();
        return userDto;
    }

    /**
     * Retrieves all expenses.
     * <p>
     * This endpoint fetches and returns a list of all expenses in the system.
     * </p>
     *
     * @return ResponseEntity containing the list of expenses
     */
    @GetMapping("/getExpensese")
    public List<Expense> getExpensese() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses;
    }

    /**
     * Retrieves all expense-user mappings.
     * <p>
     * This endpoint fetches and returns a list of all expense-user mappings in the system.
     * </p>
     *
     * @return ResponseEntity containing the list of expense-user mappings
     */
    @GetMapping("/getExpenseUser")
    public List<ExpenseUser> getExpenseUser() {
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAll();
        return expenseUsers;
    }

    /**
     * Retrieves all expense shares.
     * <p>
     * This endpoint fetches and returns a list of all expense shares in the system.
     * </p>
     *
     * @return ResponseEntity containing the list of expense shares
     */
    @GetMapping("/getExpenseShare")
    public List<ExpenseShare> getExpenseShare() {
        List<ExpenseShare> expenseShares = expenseShareRepository.findAll();
        return expenseShares;
    }


}
