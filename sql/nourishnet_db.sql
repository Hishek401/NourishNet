-- =====================================================
-- NourishNet Database Setup Script
-- Run this in phpMyAdmin: Click "SQL" tab → Paste → Go
-- =====================================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS nourishnet_db;

-- Step 2: Switch to using that database
USE nourishnet_db;

-- Step 3: Create the 'users' table
-- This stores both Donors and Admins
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    address VARCHAR(255),
    role VARCHAR(20) NOT NULL DEFAULT 'donor',
    failed_attempts INT DEFAULT 0,
    is_locked BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Step 4: Create the 'recipients' table
-- Organizations or individuals who receive donated food
CREATE TABLE IF NOT EXISTS recipients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255) NOT NULL,
    organization_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Step 5: Create the 'donations' table
-- Every food donation a donor submits
CREATE TABLE IF NOT EXISTS donations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    recipient_id INT,
    food_item VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(20) NOT NULL DEFAULT 'kg',
    category VARCHAR(50),
    expiry_date DATE,
    status VARCHAR(20) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (recipient_id) REFERENCES recipients(id) ON DELETE SET NULL
);

-- Step 6: Insert seed data
-- NOTE: Passwords below are AES encrypted using AESUtil.java
-- To regenerate, run: java com.NourishNet.util.AESUtil
-- admin123 encrypted = gP0Kq/gzrx/nTdVzVzWl6A==
-- donor123 encrypted = lzv2CIzzpkOHyI5bqCs/ug==

INSERT INTO users (full_name, email, password, phone, address, role)
VALUES ('Admin User', 'admin@nourishnet.com', 'gP0Kq/gzrx/nTdVzVzWl6A==', '9800000000', 'Islington College, Kamal Marg', 'admin');

INSERT INTO users (full_name, email, password, phone, address, role)
VALUES ('John Donor', 'john@example.com', 'lzv2CIzzpkOHyI5bqCs/ug==', '9811111111', 'Kathmandu, Thamel', 'donor');

INSERT INTO users (full_name, email, password, phone, address, role)
VALUES ('Sita Sharma', 'sita@example.com', 'lzv2CIzzpkOHyI5bqCs/ug==', '9822222222', 'Lalitpur, Patan', 'donor');

INSERT INTO users (full_name, email, password, phone, address, role)
VALUES ('Ram Thapa', 'ram@example.com', 'lzv2CIzzpkOHyI5bqCs/ug==', '9833333333', 'Bhaktapur, Durbar Square', 'donor');

-- Seed recipients
INSERT INTO recipients (name, email, phone, address, organization_type)
VALUES ('Kathmandu Food Bank', 'info@ktmfoodbank.org', '9801234567', 'Kathmandu, Baneshwor', 'Food Bank');

INSERT INTO recipients (name, email, phone, address, organization_type)
VALUES ('Hope Shelter Nepal', 'contact@hopeshelter.org', '9807654321', 'Lalitpur, Kupondole', 'Shelter');

INSERT INTO recipients (name, email, phone, address, organization_type)
VALUES ('Community Kitchen Bhaktapur', 'kitchen@community.org', '9809876543', 'Bhaktapur, Suryabinayak', 'Community Kitchen');

-- Seed donations
INSERT INTO donations (user_id, recipient_id, food_item, quantity, unit, category, expiry_date, status)
VALUES (2, 1, 'Basmati Rice', 10, 'kg', 'Grains', '2026-08-15', 'Approved');

INSERT INTO donations (user_id, recipient_id, food_item, quantity, unit, category, expiry_date, status)
VALUES (2, 2, 'Canned Beans', 20, 'cans', 'Canned', '2027-01-20', 'Pending');

INSERT INTO donations (user_id, recipient_id, food_item, quantity, unit, category, expiry_date, status)
VALUES (3, 1, 'Fresh Apples', 5, 'kg', 'Fruits', '2026-05-25', 'Approved');

INSERT INTO donations (user_id, recipient_id, food_item, quantity, unit, category, expiry_date, status)
VALUES (3, 3, 'Milk Packets', 15, 'packets', 'Dairy', '2026-05-10', 'Rejected');

INSERT INTO donations (user_id, food_item, quantity, unit, category, expiry_date, status)
VALUES (4, 'Whole Wheat Bread', 8, 'packets', 'Grains', '2026-05-12', 'Pending');
