package com.NourishNet.service;

import java.util.List;
import com.NourishNet.dao.DonationDAO;
import com.NourishNet.model.Donation;
import com.NourishNet.util.ValidationUtil;

/**
 * DonationService - Business Logic for Donation operations.
 * 
 * Handles validation and delegates to DonationDAO for database operations.
 */
public class DonationService {

    private DonationDAO donationDAO = new DonationDAO();

    /** Add a new donation with validation. Returns error message or null on success. */
    public String addDonation(Donation donation) {
        if (ValidationUtil.isNullOrEmpty(donation.getFoodItem())) {
            return "Food item name is required!";
        }
        if (donation.getQuantity() <= 0) {
            return "Quantity must be greater than zero!";
        }

        boolean success = donationDAO.addDonation(donation);
        return success ? null : "Failed to submit donation. Please try again.";
    }

    /** Get donations for a user, optionally filtered by search keyword. */
    public List<Donation> getDonationsForUser(int userId, String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return donationDAO.searchDonationsByUser(userId, keyword);
        }
        return donationDAO.getDonationsByUserId(userId);
    }

    /** Get all donations, optionally filtered by search keyword. */
    public List<Donation> getAllDonations(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return donationDAO.searchDonations(keyword);
        }
        return donationDAO.getAllDonations();
    }

    /** Get a single donation by ID. */
    public Donation getDonationById(int id) {
        return donationDAO.getDonationById(id);
    }

    /** Update donation status (admin). */
    public boolean updateStatus(int donationId, String status) {
        return donationDAO.updateStatus(donationId, status);
    }

    /** Update donation details (admin). */
    public boolean updateDonation(Donation donation) {
        return donationDAO.updateDonation(donation);
    }

    /** Cancel a pending donation (donor). */
    public boolean cancelDonation(int donationId, int userId) {
        return donationDAO.cancelDonation(donationId, userId);
    }

    /** Delete a donation (admin). */
    public boolean deleteDonation(int donationId) {
        return donationDAO.deleteDonation(donationId);
    }

    /** Count methods. */
    public int countAllDonations() { return donationDAO.countAllDonations(); }
    public int countByStatus(String status) { return donationDAO.countByStatus(status); }
    public int countByUserId(int userId) { return donationDAO.countByUserId(userId); }
}
