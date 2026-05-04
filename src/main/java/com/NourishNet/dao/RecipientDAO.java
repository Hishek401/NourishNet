package com.NourishNet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.NourishNet.config.DBConfig;
import com.NourishNet.model.Recipient;

/**
 * RecipientDAO - Data Access Object for Recipients
 * 
 * Handles ALL database operations for the "recipients" table.
 */
public class RecipientDAO {

    /** Insert a new recipient into the database. */
    public boolean addRecipient(Recipient recipient) {
        String sql = "INSERT INTO recipients (name, email, phone, address, organization_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recipient.getName());
            stmt.setString(2, recipient.getEmail());
            stmt.setString(3, recipient.getPhone());
            stmt.setString(4, recipient.getAddress());
            stmt.setString(5, recipient.getOrganizationType());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Get all recipients. */
    public List<Recipient> getAllRecipients() {
        List<Recipient> list = new ArrayList<>();
        String sql = "SELECT * FROM recipients ORDER BY created_at DESC";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Get recipient by ID. */
    public Recipient getRecipientById(int id) {
        String sql = "SELECT * FROM recipients WHERE id = ?";

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

    /** Count total recipients. */
    public int countRecipients() {
        String sql = "SELECT COUNT(*) FROM recipients";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** Helper method to map a ResultSet row to a Recipient object. */
    private Recipient mapRow(ResultSet rs) throws SQLException {
        Recipient r = new Recipient();
        r.setId(rs.getInt("id"));
        r.setName(rs.getString("name"));
        r.setEmail(rs.getString("email"));
        r.setPhone(rs.getString("phone"));
        r.setAddress(rs.getString("address"));
        r.setOrganizationType(rs.getString("organization_type"));
        r.setCreatedAt(rs.getString("created_at"));
        return r;
    }
}
