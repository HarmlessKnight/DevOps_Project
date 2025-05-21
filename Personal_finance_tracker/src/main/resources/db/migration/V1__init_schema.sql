CREATE DATABASE IF NOT EXISTS financetrackerschema;
USE financetrackerschema;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    address VARCHAR(255),
    phone_number VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE  IF NOT EXISTS roles (
    id BIGINT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE  IF NOT EXISTS user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE  IF NOT EXISTS accounts (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    account_number VARCHAR(100) NOT NULL,
    account_name VARCHAR(100),
    account_type VARCHAR(50),
    balance DECIMAL(15,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE  IF NOT EXISTS categories (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    name VARCHAR(100),
    description VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE  IF NOT EXISTS transactions (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    account_id BIGINT,
    category_id BIGINT,
    type VARCHAR(20), 
    amount DECIMAL(15,2),
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (account_id) REFERENCES accounts(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE  IF NOT EXISTS budgets (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    category_id BIGINT,
    amount DECIMAL(15,2),
    start_date DATE,
    end_date DATE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- =====================
-- Insert Sample Data
-- =====================

-- USERS
INSERT IGNORE  INTO users (id, email, password, full_name, address, phone_number, created_at)
VALUES
    (1, 'admin@example.com', '$2a$10$dummyhashedpassadmin', 'Admin User', 'Admin Street 1', '1234567890', CURRENT_TIMESTAMP),
    (2, 'user@example.com', '$2a$10$dummyhashedpassuser', 'Regular User', 'User Avenue 5', '0987654321', CURRENT_TIMESTAMP);

-- ROLES
INSERT IGNORE  INTO roles (id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

-- USER_ROLES
INSERT IGNORE  INTO user_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);

-- ACCOUNTS
INSERT IGNORE INTO accounts (id, user_id, account_number, account_name, account_type, balance, created_at)
VALUES
    (1, 2, 'ACC-001', 'Main Checking', 'Checking', 1200.50, CURRENT_TIMESTAMP);

-- CATEGORIES
INSERT IGNORE  INTO categories (id, user_id, name, description)
VALUES
    (1, 2, 'Groceries', 'Food and supermarket shopping'),
    (2, 2, 'Utilities', 'Monthly bills and services');

-- TRANSACTIONS
INSERT IGNORE  INTO transactions (id, user_id, account_id, category_id, type, amount, description, created_at)
VALUES
    (1, 2, 1, 1, 'EXPENSE', 75.25, 'Supermarket shopping', CURRENT_TIMESTAMP),
    (2, 2, 1, 2, 'EXPENSE', 50.00, 'Electricity bill', CURRENT_TIMESTAMP);

-- BUDGETS
INSERT IGNORE  INTO budgets (id, user_id, category_id, amount, start_date, end_date)
VALUES
    (1, 2, 1, 300.00, CURDATE(), LAST_DAY(CURDATE()));
