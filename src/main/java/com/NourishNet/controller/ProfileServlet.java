package com.NourishNet.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

import com.NourishNet.model.User;
import com.NourishNet.service.UserService;

/**
 * ProfileServlet - View and update user profile.
 * 
 * URL: /donor/profile
 * GET  → Show profile page
 * POST → Update profile details
 */
public class ProfileServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        User profileUser = userService.getUserById(userId);
        request.setAttribute("profileUser", profileUser);
        request.setAttribute("pageTitle", "My Profile");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/profile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        String error = userService.updateProfile(userId, fullName, phone, address);

        if (error == null) {
            // Update session name
            session.setAttribute("userName", fullName);
            request.setAttribute("success", "Profile updated successfully!");
        } else {
            request.setAttribute("error", error);
        }

        User profileUser = userService.getUserById(userId);
        request.setAttribute("profileUser", profileUser);
        request.setAttribute("pageTitle", "My Profile");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/profile.jsp");
        dispatcher.forward(request, response);
    }
}
