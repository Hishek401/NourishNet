package com.NourishNet.model;

/**
 * User Model Class
 * 
 * A JavaBean that represents a user in the system.
 * Stores data like name, email, role, and account lockout info.
 */
public class User {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String role;
    private int failedAttempts;
    private boolean isLocked;
    private String createdAt;

    // No-arg constructor (required for JavaBean)
    public User() {
    }

    // Parameterized constructor for registration
    public User(String fullName, String email, String password, String phone, String address, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getFailedAttempts() { return failedAttempts; }
    public void setFailedAttempts(int failedAttempts) { this.failedAttempts = failedAttempts; }

    public boolean getIsLocked() { return isLocked; }
    public void setIsLocked(boolean isLocked) { this.isLocked = isLocked; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
