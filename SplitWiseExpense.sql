-- 1. Create the database
CREATE DATABASE IF NOT EXISTS splitwise_clone;

-- 2. Use the database
USE splitwise_clone;

-- 3. Create the users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE  -- Add UNIQUE constraint to 'name' to ensure indexing
);

-- 4. Insert sample user
INSERT INTO users (name) VALUES ('Chitti Talli');
INSERT INTO users (name) VALUES ('chitti babu');
INSERT INTO users (name) VALUES ('manish');
INSERT INTO users (name) VALUES ('keerthi');
INSERT INTO users (name) VALUES ('nikki');
INSERT INTO users (name) VALUES ('bakki');


CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    expense_date DATE NOT NULL,
    group_name VARCHAR(100) NOT NULL,
    expense_name VARCHAR(100) NOT NULL UNIQUE,  -- Add UNIQUE constraint to ensure indexing
    total_amount DECIMAL(10, 2) NOT NULL
);
ALTER TABLE expenses
ADD COLUMN created_by VARCHAR(100) NOT NULL,
ADD COLUMN split_type ENUM('EQUAL', 'PERCENTAGE', 'MANUAL') NOT NULL DEFAULT 'EQUAL',
ADD FOREIGN KEY (created_by) REFERENCES users(name) ON DELETE CASCADE;

INSERT INTO expenses (expense_date, group_name, expense_name, total_amount, created_by, split_type)
VALUES ('2025-05-01', 'Trip to Goa', 'Hotel', 3000, 'manish', 'EQUAL');


CREATE TABLE IF NOT EXISTS expense_user (
    expense_name VARCHAR(100),
    user_name VARCHAR(100),
    PRIMARY KEY (expense_name, user_name),
    FOREIGN KEY (expense_name) REFERENCES expenses(expense_name) ON DELETE CASCADE,
    FOREIGN KEY (user_name) REFERENCES users(name) ON DELETE CASCADE
);


-- Insert data into expense_user
INSERT INTO expense_user (expense_name, user_name)
VALUES
('Hotel', 'manish'),
('Hotel', 'keerthi'),
('Hotel', 'nikki');


CREATE TABLE IF NOT EXISTS expense_share (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    expense_name VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    percentage DECIMAL(5, 2),     -- For percentage-based splits (0.00 to 100.00)
    amount DECIMAL(10, 2),        -- For manual splits (fixed amount)
    UNIQUE (expense_name, user_name),  -- Ensures each user-expense combo is unique
    FOREIGN KEY (expense_name) REFERENCES expenses(expense_name) ON DELETE CASCADE,
    FOREIGN KEY (user_name) REFERENCES users(name) ON DELETE CASCADE
);


SELECT 
    e.expense_date,
    e.group_name,
    e.expense_name,
    e.total_amount,
    e.created_by,
    e.split_type,
    u.name AS user_name,
    -- Calculate share
    CASE 
        WHEN e.created_by = u.name THEN  -- If user created the expense
            e.total_amount - (e.total_amount / (SELECT COUNT(*) FROM expense_user eu WHERE eu.expense_name = e.expense_name))
        ELSE  -- User is a participant, not the creator
            -(e.total_amount / (SELECT COUNT(*) FROM expense_user eu WHERE eu.expense_name = e.expense_name))
    END AS share
FROM 
    expenses e
JOIN 
    expense_user eu ON e.expense_name = eu.expense_name
JOIN 
    users u ON eu.user_name = u.name
WHERE 
    u.name = 'keerthi'  -- Specific user whose view we want
GROUP BY 
    e.expense_name, e.expense_date, e.group_name, e.total_amount, e.created_by, e.split_type, u.name;


select * from users;
select * from expense_share;
select * from expense_user;
select * from expenses;
