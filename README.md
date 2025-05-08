# Splitwise Expense Application

A Spring Boot application that helps users manage and split expenses within groups. Built with Java 17 and Spring Boot 3.4.3.

## Technical Stack

- Java 17
- Spring Boot 3.4.3
- MySQL Database
- Maven 3.8.1
- Docker
- Springdoc OpenAPI UI 2.5.0
- JPA/Hibernate

## Project Structure

### Core Entities

- **User**: Basic user information with unique name constraint
- **Expense**: Expense details with amount and split information
- **ExpenseShare**: Tracks individual shares in expenses
- **ExpenseUser**: Manages expense-user relationships

### Database Schema

- `users`: User records
- `expenses`: Expense details
- `expense_share`: Individual expense shares
- `expense_user`: Expense-user mappings

## Getting Started

### Prerequisites

- Java 17
- Maven 3.8.x
- MySQL Server
- Docker (optional)

### Local Setup

1. Clone the repository
2. Configure MySQL database in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/splitwise
   spring.datasource.username=your_username
   spring.datasource.password=your_password
    ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at `http://localhost:8080`
5. Open API documentation at `http://localhost:8080/swagger-ui/index.html`
6. Use Postman or any API client to test the endpoints
7. Example API calls:
   - Create User: `POST /api/users`
   - Create Expense: `POST /api/expenses`
   - Get All Users: `GET /api/users`
   - Get All Expenses: `GET /api/expenses`
   - Get User Expenses: `GET /api/users/{userId}/expenses`
   - Get Expense Shares: `GET /api/expenses/{expenseId}/shares`
   - Get User Shares: `GET /api/users/{userId}/shares`
   - Get User Balance: `GET /api/users/{userId}/balance`
   - Get User Balance with Group: `GET /api/users/{userId}/balance/{groupId}`
   - Get User Balance with Group and Date: `GET /api/users/{userId}/balance/{groupId}/{date}`
   - Get User Balance with Date: `GET /api/users/{userId}/balance/{date}`

Docker Setup:
1.Create Docker network:
    docker network create splitwise-network
2.Run MySQL container:
    docker run -d \
      --name mysql \
      --network splitwise-network \
      -e MYSQL_ROOT_PASSWORD=root \
      -e MYSQL_DATABASE=splitwise \
      -p 3306:3306 \
      mysql:8
3.Build application image:
    docker build -t splitwise-app .
4.Run application container:
    docker run -d \
      --name splitwise \
      --network splitwise-network \
      -p 8080:8080 \
      -u nonroot \
      splitwise-app