package com.NourishNet.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import com.NourishNet.service.UserService;

/**
 * RegisterServlet - Handles user registration with validation and AES encryption.
 * 
 * URL: /register
 * GET  → Show the registration form
 * POST → Validate and create a new account
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pageTitle", "Register");
        request.setAttribute("pageType", "auth");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Check password confirmation first
        if (password != null && !password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match!");
            request.setAttribute("pageTitle", "Register");
            request.setAttribute("pageType", "auth");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Delegate to service layer for validation + registration
        String error = userService.registerUser(fullName, email, password, phone, address);

        if (error == null) {
            // Success — redirect to login
            response.sendRedirect(request.getContextPath() + "/login?registered=true");
        } else {
            request.setAttribute("error", error);
            request.setAttribute("pageTitle", "Register");
            request.setAttribute("pageType", "auth");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            dispatcher.forward(request, response);
        }
    }
}
