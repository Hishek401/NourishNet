package com.NourishNet.service;

import java.util.List;
import com.NourishNet.dao.UserDAO;
import com.NourishNet.model.User;
import com.NourishNet.util.AESUtil;
import com.NourishNet.util.ValidationUtil;

/**
 * UserService - Business Logic for User operations.
 * 
 * Sits between Controllers and DAO layer.
 * Handles validation, encryption, and business rules.
 */
public class UserService {

    private UserDAO userDAO = new UserDAO();

    private static final int MAX_FAILED_ATTEMPTS = 5;

    /** Register a new user with validation and encryption. Returns error message or null on success. */
    public String registerUser(String fullName, String email, String password, String phone, String address) {
        if (ValidationUtil.isNullOrEmpty(fullName) || ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
            return "Full name, email, and password are required!";
        }
        if (ValidationUtil.containsDigits(fullName)) {
            return "Name must not contain numbers!";
        }
        if (!ValidationUtil.isValidEmail(email)) {
            return "Please enter a valid email address!";
        }
        if (!ValidationUtil.isValidPassword(password)) {
            return "Password must be at least 6 characters!";
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            return "Phone number must be exactly 10 digits!";
        }
        if (userDAO.emailExists(email)) {
            return "This email is already registered!";
        }
        if (phone != null && !phone.trim().isEmpty() && userDAO.phoneExists(phone)) {
            return "This phone number is already registered!";
        }

        String encryptedPassword = AESUtil.encrypt(password);
        User user = new User(fullName, email, encryptedPassword, phone, address, "donor");
        boolean success = userDAO.register(user);
        return success ? null : "Registration failed. Please try again.";
    }

    /** Authenticate user. Returns User on success, null on failure. Sets error message via array. */
    public User authenticateUser(String email, String password, String[] errorHolder) {
        if (ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(password)) {
            errorHolder[0] = "Email and password are required!";
            return null;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            errorHolder[0] = "Invalid email or password!";
            return null;
        }

        // Check if account is locked
        if (user.getIsLocked()) {
            errorHolder[0] = "Your account is locked due to too many failed login attempts. Please contact admin.";
            return null;
        }

        // Encrypt input password and compare with stored
        String encryptedInput = AESUtil.encrypt(password);
        if (encryptedInput != null && encryptedInput.equals(user.getPassword())) {
            // Successful login - reset failed attempts
            userDAO.resetFailedAttempts(email);
            return user;
        } else {
            // Failed login - increment attempts
            userDAO.incrementFailedAttempts(email);
            int newAttempts = user.getFailedAttempts() + 1;
            if (newAttempts >= MAX_FAILED_ATTEMPTS) {
                userDAO.lockAccount(email);
                errorHolder[0] = "Account locked! Too many failed attempts. Contact admin to unlock.";
            } else {
                int remaining = MAX_FAILED_ATTEMPTS - newAttempts;
                errorHolder[0] = "Invalid email or password! " + remaining + " attempt(s) remaining.";
            }
            return null;
        }
    }

    /** Update user profile. Returns error message or null on success. */
    public String updateProfile(int userId, String fullName, String phone, String address) {
        if (ValidationUtil.isNullOrEmpty(fullName)) {
            return "Full name is required!";
        }
        if (ValidationUtil.containsDigits(fullName)) {
            return "Name must not contain numbers!";
        }
        if (!ValidationUtil.isValidPhone(phone)) {
            return "Phone number must be exactly 10 digits!";
        }
        if (phone != null && !phone.trim().isEmpty() && userDAO.phoneExistsForOtherUser(phone, userId)) {
            return "This phone number is already in use by another user!";
        }

        boolean success = userDAO.updateProfile(userId, fullName, phone, address);
        return success ? null : "Failed to update profile.";
    }

    /** Change password. Returns error message or null on success. */
    public String changePassword(int userId, String oldPassword, String newPassword, String confirmPassword) {
        if (ValidationUtil.isNullOrEmpty(oldPassword) || ValidationUtil.isNullOrEmpty(newPassword)) {
            return "All password fields are required!";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "New passwords do not match!";
        }
        if (!ValidationUtil.isValidPassword(newPassword)) {
            return "New password must be at least 6 characters!";
        }

        User user = userDAO.getUserById(userId);
        if (user == null) {
            return "User not found!";
        }

        String encryptedOld = AESUtil.encrypt(oldPassword);
        if (!encryptedOld.equals(user.getPassword())) {
            return "Current password is incorrect!";
        }

        String encryptedNew = AESUtil.encrypt(newPassword);
        boolean success = userDAO.updatePassword(userId, encryptedNew);
        return success ? null : "Failed to change password.";
    }

    /** Reset password by email (forgot password). */
    public String resetPassword(String email, String newPassword, String confirmPassword) {
        if (ValidationUtil.isNullOrEmpty(email) || ValidationUtil.isNullOrEmpty(newPassword)) {
            return "All fields are required!";
        }
        if (!newPassword.equals(confirmPassword)) {
            return "Passwords do not match!";
        }
        if (!ValidationUtil.isValidPassword(newPassword)) {
            return "Password must be at least 6 characters!";
        }
        if (!userDAO.emailExists(email)) {
            return "No account found with this email!";
        }

        String encrypted = AESUtil.encrypt(newPassword);
        boolean success = userDAO.resetPasswordByEmail(email, encrypted);
        return success ? null : "Failed to reset password.";
    }

    /** Get all donors. */
    public List<User> getAllDonors() {
        return userDAO.getAllDonors();
    }

    /** Get user by ID. */
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    /** Delete donor. */
    public boolean deleteDonor(int id) {
        return userDAO.deleteDonor(id);
    }

    /** Unlock a locked account (admin action). */
    public boolean unlockAccount(int userId) {
        return userDAO.unlockAccount(userId);
    }

    /** Count donors. */
    public int countDonors() {
        return userDAO.countDonors();
    }
}
