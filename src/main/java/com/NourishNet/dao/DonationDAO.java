package com.NourishNet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.NourishNet.config.DBConfig;
import com.NourishNet.model.Donation;

/**
 * DonationDAO - Data Access Object for Donations
 * 
 * Handles ALL database operations for the "donations" table.
 */
public class DonationDAO {

    /** Add a new donation. */
    public boolean addDonation(Donation donation) {
        String sql = "INSERT INTO donations (user_id, recipient_id, food_item, quantity, unit, category, expiry_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donation.getUserId());
            if (donation.getRecipientId() > 0) {
                stmt.setInt(2, donation.getRecipientId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, donation.getFoodItem());
            stmt.setInt(4, donation.getQuantity());
            stmt.setString(5, donation.getUnit());
            stmt.setString(6, donation.getCategory());
            stmt.setString(7, donation.getExpiryDate());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Get donations for a specific user. */
    public List<Donation> getDonationsByUserId(int userId) {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT d.*, r.name AS recipient_name FROM donations d " +
                     "LEFT JOIN recipients r ON d.recipient_id = r.id " +
                     "WHERE d.user_id = ? ORDER BY d.created_at DESC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                donations.add(mapRow(rs, false));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    /** Get all donations with donor names (admin view). */
    public List<Donation> getAllDonations() {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT d.*, u.full_name AS donor_name, r.name AS recipient_name FROM donations d " +
                     "JOIN users u ON d.user_id = u.id " +
                     "LEFT JOIN recipients r ON d.recipient_id = r.id " +
                     "ORDER BY d.created_at DESC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                donations.add(mapRow(rs, true));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    /** Search donations by food item name using LIKE. */
    public List<Donation> searchDonations(String keyword) {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT d.*, u.full_name AS donor_name, r.name AS recipient_name FROM donations d " +
                     "JOIN users u ON d.user_id = u.id " +
                     "LEFT JOIN recipients r ON d.recipient_id = r.id " +
                     "WHERE d.food_item LIKE ? OR d.category LIKE ? OR u.full_name LIKE ? " +
                     "ORDER BY d.created_at DESC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchTerm = "%" + keyword + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                donations.add(mapRow(rs, true));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    /** Search donations for a specific user. */
    public List<Donation> searchDonationsByUser(int userId, String keyword) {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT d.*, r.name AS recipient_name FROM donations d " +
                     "LEFT JOIN recipients r ON d.recipient_id = r.id " +
                     "WHERE d.user_id = ? AND (d.food_item LIKE ? OR d.category LIKE ?) " +
                     "ORDER BY d.created_at DESC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            String searchTerm = "%" + keyword + "%";
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                donations.add(mapRow(rs, false));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return donations;
    }

    /** Get a single donation by ID. */
    public Donation getDonationById(int id) {
        String sql = "SELECT d.*, u.full_name AS donor_name, r.name AS recipient_name FROM donations d " +
                     "JOIN users u ON d.user_id = u.id " +
                     "LEFT JOIN recipients r ON d.recipient_id = r.id " +
                     "WHERE d.id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs, true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Update donation status (approve/reject). */
    public boolean updateStatus(int donationId, String status) {
        String sql = "UPDATE donations SET status = ? WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, donationId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Update donation details (admin edit). */
    public boolean updateDonation(Donation donation) {
        String sql = "UPDATE donations SET food_item = ?, quantity = ?, unit = ?, category = ?, expiry_date = ?, recipient_id = ? WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, donation.getFoodItem());
            stmt.setInt(2, donation.getQuantity());
            stmt.setString(3, donation.getUnit());
            stmt.setString(4, donation.getCategory());
            stmt.setString(5, donation.getExpiryDate());
            if (donation.getRecipientId() > 0) {
                stmt.setInt(6, donation.getRecipientId());
            } else {
                stmt.setNull(6, java.sql.Types.INTEGER);
            }
            stmt.setInt(7, donation.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Cancel a donation (only if Pending, by the donor themselves). */
    public boolean cancelDonation(int donationId, int userId) {
        String sql = "UPDATE donations SET status = 'Cancelled' WHERE id = ? AND user_id = ? AND status = 'Pending'";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donationId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Delete a donation. */
    public boolean deleteDonation(int donationId) {
        String sql = "DELETE FROM donations WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donationId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Count all donations. */
    public int countAllDonations() {
        return countBySql("SELECT COUNT(*) FROM donations");
    }

    /** Count donations by status. */
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM donations WHERE status = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Count donations by user. */
    public int countByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM donations WHERE user_id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Helper to count by a simple SQL. */
    private int countBySql(String sql) {
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Map ResultSet row to Donation. */
    private Donation mapRow(ResultSet rs, boolean includeDonorName) throws SQLException {
        Donation d = new Donation();
        d.setId(rs.getInt("id"));
        d.setUserId(rs.getInt("user_id"));
        d.setRecipientId(rs.getInt("recipient_id"));
        d.setFoodItem(rs.getString("food_item"));
        d.setQuantity(rs.getInt("quantity"));
        d.setUnit(rs.getString("unit"));
        d.setCategory(rs.getString("category"));
        d.setExpiryDate(rs.getString("expiry_date"));
        d.setStatus(rs.getString("status"));
        d.setCreatedAt(rs.getString("created_at"));
        if (includeDonorName) {
            d.setDonorName(rs.getString("donor_name"));
        }
        try {
            d.setRecipientName(rs.getString("recipient_name"));
        } catch (SQLException e) {
            // recipient_name column may not be in all queries
        }
        return d;
    }
}
