package com.NourishNet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.NourishNet.config.DBConfig;
import com.NourishNet.model.User;

/**
 * UserDAO - Data Access Object for Users
 * 
 * Handles ALL database operations for the "users" table.
 */
public class UserDAO {

    /** Register a new user (insert into users table). */
    public boolean register(User user) {
        String sql = "INSERT INTO users (full_name, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getRole());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Find user by email (for login and forgot password). */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Get all donors (role = 'donor'). */
    public List<User> getAllDonors() {
        return searchDonors(null);
    }

    /** Search donors by name or email. */
    public List<User> searchDonors(String query) {
        List<User> donors = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'donor'";
        
        if (query != null && !query.trim().isEmpty()) {
            sql += " AND (full_name LIKE ? OR email LIKE ?)";
        }
        sql += " ORDER BY created_at DESC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (query != null && !query.trim().isEmpty()) {
                String searchPattern = "%" + query.trim() + "%";
                stmt.setString(1, searchPattern);
                stmt.setString(2, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                donors.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donors;
    }

    /** Get a single user by ID. */
    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Delete a donor by ID. */
    public boolean deleteDonor(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND role = 'donor'";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Count total donors. */
    public int countDonors() {
        String sql = "SELECT COUNT(*) FROM users WHERE role = 'donor'";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Check if email already exists in the database. */
    public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Check if phone number already exists in the database. */
    public boolean phoneExists(String phone) {
        String sql = "SELECT id FROM users WHERE phone = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Check if phone exists for a different user (used during profile update). */
    public boolean phoneExistsForOtherUser(String phone, int userId) {
        String sql = "SELECT id FROM users WHERE phone = ? AND id != ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Update user profile (name, phone, address). */
    public boolean updateProfile(int userId, String fullName, String phone, String address) {
        String sql = "UPDATE users SET full_name = ?, phone = ?, address = ? WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fullName);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setInt(4, userId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Update user password. */
    public boolean updatePassword(int userId, String newEncryptedPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newEncryptedPassword);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Reset password by email (for forgot password). */
    public boolean resetPasswordByEmail(String email, String newEncryptedPassword) {
        String sql = "UPDATE users SET password = ?, failed_attempts = 0, is_locked = FALSE WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newEncryptedPassword);
            stmt.setString(2, email);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Increment failed login attempts for a user. */
    public void incrementFailedAttempts(String email) {
        String sql = "UPDATE users SET failed_attempts = failed_attempts + 1 WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Lock a user account (after too many failed attempts). */
    public void lockAccount(String email) {
        String sql = "UPDATE users SET is_locked = TRUE WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Reset failed attempts on successful login. */
    public void resetFailedAttempts(String email) {
        String sql = "UPDATE users SET failed_attempts = 0 WHERE email = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Unlock a user account (admin action). */
    public boolean unlockAccount(int userId) {
        String sql = "UPDATE users SET is_locked = FALSE, failed_attempts = 0 WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Helper method to map a ResultSet row to a User object. */
    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setRole(rs.getString("role"));
        user.setFailedAttempts(rs.getInt("failed_attempts"));
        user.setIsLocked(rs.getBoolean("is_locked"));
        user.setCreatedAt(rs.getString("created_at"));
        return user;
    }
}
