package com.NourishNet.controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

import com.NourishNet.model.User;
import com.NourishNet.model.Donation;
import com.NourishNet.model.Recipient;
import com.NourishNet.service.UserService;
import com.NourishNet.service.DonationService;
import com.NourishNet.dao.RecipientDAO;

/**
 * AdminDashboardServlet - Admin dashboard with full CRUD, search, and user management.
 * 
 * URL: /admin/dashboard
 * GET  → Show stats, all donations, all donors, recipients
 * POST → Handle approve/reject/delete/unlock/create/update actions
 */
public class AdminDashboardServlet extends HttpServlet {

    private UserService userService = new UserService();
    private DonationService donationService = new DonationService();
    private RecipientDAO recipientDAO = new RecipientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Search parameters
        String search = request.getParameter("search");
        String donorSearch = request.getParameter("donorSearch");

        // If an editId is provided, show the edit page instead of the dashboard
        String editIdStr = request.getParameter("editId");
        if (editIdStr != null && !editIdStr.isEmpty()) {
            int editId = Integer.parseInt(editIdStr);
            Donation donation = donationService.getDonationById(editId);
            if (donation != null) {
                request.setAttribute("donation", donation);
                request.setAttribute("recipients", recipientDAO.getAllRecipients());
                request.setAttribute("pageTitle", "Edit Donation");
                request.setAttribute("pageType", "admin");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/edit-donation.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        List<User> donors = userService.searchDonors(donorSearch);
        List<Donation> donations = donationService.getAllDonations(search);
        List<Recipient> recipients = recipientDAO.getAllRecipients();

        int totalDonors = userService.countDonors();
        int totalDonations = donationService.countAllDonations();
        int approvedCount = donationService.countByStatus("Approved");
        int pendingCount = donationService.countByStatus("Pending");
        int rejectedCount = donationService.countByStatus("Rejected");
        int recipientCount = recipientDAO.countRecipients();

        request.setAttribute("donors", donors);
        request.setAttribute("donations", donations);
        request.setAttribute("recipients", recipients);
        request.setAttribute("totalDonors", totalDonors);
        request.setAttribute("totalDonations", totalDonations);
        request.setAttribute("approvedCount", approvedCount);
        request.setAttribute("pendingCount", pendingCount);
        request.setAttribute("rejectedCount", rejectedCount);
        request.setAttribute("recipientCount", recipientCount);
        request.setAttribute("searchQuery", search);
        request.setAttribute("donorSearchQuery", donorSearch);

        request.setAttribute("pageTitle", "Admin Dashboard");
        request.setAttribute("pageType", "admin");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/admin-dashboard.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("approve".equals(action) || "reject".equals(action)) {
            int donationId = Integer.parseInt(request.getParameter("donationId"));
            String status = "approve".equals(action) ? "Approved" : "Rejected";
            donationService.updateStatus(donationId, status);

        } else if ("deleteDonor".equals(action)) {
            int donorId = Integer.parseInt(request.getParameter("donorId"));
            userService.deleteDonor(donorId);

        } else if ("unlockAccount".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            userService.unlockAccount(userId);

        } else if ("updateDonation".equals(action)) {
            // Admin editing donation details
            Donation d = new Donation();
            d.setId(Integer.parseInt(request.getParameter("donationId")));
            d.setFoodItem(request.getParameter("foodItem"));
            d.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            d.setUnit(request.getParameter("unit"));
            d.setCategory(request.getParameter("category"));
            d.setExpiryDate(request.getParameter("expiryDate"));
            String recipientIdStr = request.getParameter("recipientId");
            if (recipientIdStr != null && !recipientIdStr.isEmpty()) {
                d.setRecipientId(Integer.parseInt(recipientIdStr));
            }
            donationService.updateDonation(d);

        } else if ("deleteDonation".equals(action)) {
            int donationId = Integer.parseInt(request.getParameter("donationId"));
            donationService.deleteDonation(donationId);
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}
