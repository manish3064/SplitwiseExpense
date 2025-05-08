package com.expenses.splitwise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the MySQL database connection.
 * <p>
 * This class is responsible for creating and configuring the {@link DataSource} bean
 * used by the application to interact with the MySQL database. The database connection
 * properties such as URL, username, and password are injected from the application's
 * configuration file (e.g., `application.properties` or `application.yml`) using
 * Spring's {@link Value} annotation.
 * </p>
 */
@Configuration
public class MySQLConfig {

    /**
     * The URL of the MySQL database.
     * <p>
     * This property is injected from the configuration file using the key
     * `spring.datasource.url`. It specifies the JDBC URL for connecting to the
     * database, including the hostname, port, and database name.
     * </p>
     */
    @Value("${spring.datasource.url}")
    private String dbUrl;

    /**
     * The username for the MySQL database.
     * <p>
     * This property is injected from the configuration file using the key
     * `spring.datasource.username`. It specifies the username required to
     * authenticate with the database.
     * </p>
     */
    @Value("${spring.datasource.username}")
    private String dbUsername;

    /**
     * The password for the MySQL database.
     * <p>
     * This property is injected from the configuration file using the key
     * `spring.datasource.password`. It specifies the password required to
     * authenticate with the database.
     * </p>
     */
    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * Creates and configures the {@link DataSource} bean for connecting to the MySQL database.
     * <p>
     * This method initializes a {@link DriverManagerDataSource} instance with the
     * database connection properties (URL, username, and password). The driver class
     * name is set to `com.mysql.cj.jdbc.Driver`, which is the official MySQL JDBC driver.
     * </p>
     *
     * @return the configured {@link DataSource} instance for database interactions
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(dbUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);
        return ds;
    }
}
