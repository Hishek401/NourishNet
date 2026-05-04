package com.NourishNet.util;

/**
 * ValidationUtil - Input Validation Utility Class
 * 
 * Provides reusable validation methods for form input checking.
 * Used by servlets before inserting data into the database.
 */
public class ValidationUtil {

    /**
     * Checks if a string is null or empty (after trimming whitespace).
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Checks if a name contains any digits.
     * Names should only contain letters, spaces, and basic punctuation.
     */
    public static boolean containsDigits(String value) {
        if (value == null) return false;
        return value.matches(".*\\d.*");
    }

    /**
     * Validates email format using a simple regex pattern.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Validates phone number (must be 10 digits).
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) return true; // phone is optional
        return phone.matches("^\\d{10}$");
    }

    /**
     * Validates that a password meets minimum length requirement.
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
