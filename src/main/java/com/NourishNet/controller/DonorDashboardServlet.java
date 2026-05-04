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
import com.NourishNet.service.DonationService;

/**
 * DonorDashboardServlet - Donor dashboard with search and cancel.
 * 
 * URL: /donor/dashboard
 */
@WebServlet("/donor/dashboard")
public class DonorDashboardServlet extends HttpServlet {

    private DonationService donationService = new DonationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String search = request.getParameter("search");

        List<Donation> donations = donationService.getDonationsForUser(userId, search);
        int totalDonations = donationService.countByUserId(userId);

        int approvedCount = 0, pendingCount = 0;
        for (Donation d : donations) {
            if ("Approved".equals(d.getStatus())) approvedCount++;
            if ("Pending".equals(d.getStatus())) pendingCount++;
        }

        request.setAttribute("donations", donations);
        request.setAttribute("totalDonations", totalDonations);
        request.setAttribute("approvedCount", approvedCount);
        request.setAttribute("pendingCount", pendingCount);
        request.setAttribute("searchQuery", search);

        request.setAttribute("pageTitle", "Donor Dashboard");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/donor-dashboard.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        if ("cancel".equals(action)) {
            int donationId = Integer.parseInt(request.getParameter("donationId"));
            donationService.cancelDonation(donationId, userId);
        }

        response.sendRedirect(request.getContextPath() + "/donor/dashboard");
    }
}
