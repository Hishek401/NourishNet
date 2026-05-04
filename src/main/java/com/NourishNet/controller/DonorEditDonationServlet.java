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

public class DonorEditDonationServlet extends HttpServlet {

    private DonationService donationService = new DonationService();
    private RecipientDAO recipientDAO = new RecipientDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/donor/dashboard");
            return;
        }

        int donationId = Integer.parseInt(idStr);
        Donation donation = donationService.getDonationById(donationId);

        if (donation == null || donation.getUserId() != userId || !"Pending".equals(donation.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/donor/dashboard");
            return;
        }

        List<Recipient> recipients = recipientDAO.getAllRecipients();
        request.setAttribute("recipients", recipients);
        request.setAttribute("donation", donation);
        request.setAttribute("pageTitle", "Edit Donation");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/donor-edit-donation.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        int donationId = Integer.parseInt(request.getParameter("donationId"));
        Donation existingDonation = donationService.getDonationById(donationId);

        if (existingDonation == null || existingDonation.getUserId() != userId || !"Pending".equals(existingDonation.getStatus())) {
            response.sendRedirect(request.getContextPath() + "/donor/dashboard");
            return;
        }

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
            request.setAttribute("donation", existingDonation);
            request.setAttribute("pageTitle", "Edit Donation");
            request.setAttribute("pageType", "donor");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/donor-edit-donation.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Donation donation = new Donation();
        donation.setId(donationId);
        donation.setUserId(userId);
        donation.setFoodItem(foodItem);
        donation.setQuantity(quantity);
        donation.setUnit(unit);
        donation.setCategory(category);
        donation.setExpiryDate(expiryDate);
        if (recipientIdStr != null && !recipientIdStr.isEmpty()) {
            donation.setRecipientId(Integer.parseInt(recipientIdStr));
        }

        donationService.updateDonation(donation);

        response.sendRedirect(request.getContextPath() + "/donor/dashboard?updated=true");
    }
}
