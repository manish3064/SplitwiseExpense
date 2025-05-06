package com.expenses.splitwise.controller;

import com.expenses.splitwise.dto.*;
import com.expenses.splitwise.entity.*;
import com.expenses.splitwise.repository.*;
import com.expenses.splitwise.service.*;
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

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ExpenseService expenseService;
    private final ExpenseUserService expenseUserService;
    private final ExpenseShareRepository expenseShareRepository;
    private final UserRepository userRepository;
    private final ExpenseUserRepository expenseUserRepository;
    private final ExpenseRepository expenseRepository;

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
            UserDto createdUser = userService.createUser(userDto);
            logger.info("Created user: {}", createdUser);
            String message ="Successfully Created user: " + createdUser ;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            logger.error("Error creating user", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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
            ExpenseDto createdExpense = expenseService.createExpense(expenseDto);
            log.info("Successfully created expense: {}", createdExpense);
            String message ="Successfully created expense: " + createdExpense ;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            log.error("Failed to create expense: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create expense: " + e.getMessage());
        }
    }

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
            expenseUserService.createExpenseUserMapping(expenseName, userNames);
            log.info("Successfully added users to expense: {}", expenseName);
            String message ="Successfully added users to expense: " + expenseName + " with users: " + request;
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", message));
        } catch (Exception e) {
            log.error("Failed to add users to expense {}: {}", expenseName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add users to expense: " + e.getMessage());
        }

    }

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
            List<ExpenseShareDto> shares = userService.calculateUserExpenseShares(userName);
            log.info("Successfully retrieved {} expense shares for user: {}", shares.size(), userName);
            return ResponseEntity.ok(shares);
        } catch (Exception e) {
            log.error("Failed to retrieve expense shares for user {}: {}", userName, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve expense shares: " + e.getMessage());
        }

    }

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


    @GetMapping("/getUsers")
    public List<User> getUser() {
        List<User> userDto= userRepository.findAll();
        return userDto;
    }

    @GetMapping("/getExpensese")
    public List<Expense> getExpensese() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses;
    }

    @GetMapping("/getExpenseUser")
    public List<ExpenseUser> getExpenseUser() {
        List<ExpenseUser> expenseUsers = expenseUserRepository.findAll();
        return expenseUsers;
    }

    @GetMapping("/getExpenseShare")
    public List<ExpenseShare> getExpenseShare() {
        List<ExpenseShare> expenseShares = expenseShareRepository.findAll();
        return expenseShares;
    }


}
