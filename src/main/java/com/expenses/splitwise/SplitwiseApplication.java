package com.expenses.splitwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Splitwise expense sharing application.
 * <p>
 * This Spring Boot application provides functionality for:
 * - Managing user expenses
 * - Calculating expense shares
 * - Tracking shared payments
 * </p>
 */
@SpringBootApplication
public class SplitwiseApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 *
	 * @param args command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}
}
