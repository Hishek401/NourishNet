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

import com.NourishNet.model.Donation;
import com.NourishNet.model.Recipient;
import com.NourishNet.service.DonationService;
import com.NourishNet.dao.RecipientDAO;

/**
 * SubmitDonationServlet - Handles the donation submission form.
 * 
 * URL: /donor/donate
 * GET  → Show the donation form
 * POST → Save the donation to the database
 */
@WebServlet("/donor/donate")
public class SubmitDonationServlet extends HttpServlet {

    private DonationService donationService = new DonationService();
    private RecipientDAO recipientDAO = new RecipientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Provide recipients list for the dropdown
        List<Recipient> recipients = recipientDAO.getAllRecipients();
        request.setAttribute("recipients", recipients);
        request.setAttribute("pageTitle", "Submit Donation");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/submit-donation.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String foodItem = request.getParameter("foodItem");
        String quantityStr = request.getParameter("quantity");
        String unit = request.getParameter("unit");
        String category = request.getParameter("category");
        String expiryDate = request.getParameter("expiryDate");
        String recipientIdStr = request.getParameter("recipientId");

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Quantity must be a valid number!");
            request.setAttribute("recipients", recipientDAO.getAllRecipients());
            request.setAttribute("pageTitle", "Submit Donation");
            request.setAttribute("pageType", "donor");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/submit-donation.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Donation donation = new Donation();
        donation.setUserId(userId);
        donation.setFoodItem(foodItem);
        donation.setQuantity(quantity);
        donation.setUnit(unit);
        donation.setCategory(category);
        donation.setExpiryDate(expiryDate);
        if (recipientIdStr != null && !recipientIdStr.isEmpty()) {
            donation.setRecipientId(Integer.parseInt(recipientIdStr));
        }

        String error = donationService.addDonation(donation);

        if (error == null) {
            response.sendRedirect(request.getContextPath() + "/donor/dashboard?donated=true");
        } else {
            request.setAttribute("error", error);
            request.setAttribute("recipients", recipientDAO.getAllRecipients());
            request.setAttribute("pageTitle", "Submit Donation");
            request.setAttribute("pageType", "donor");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/submit-donation.jsp");
            dispatcher.forward(request, response);
        }
    }
}
