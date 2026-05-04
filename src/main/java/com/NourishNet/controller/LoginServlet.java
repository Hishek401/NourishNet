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
 * LoginServlet - Handles user login with AES encryption and account lockout.
 * 
 * URL: /login
 * GET  → Show the login form
 * POST → Check credentials and log the user in
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String registered = request.getParameter("registered");
        if ("true".equals(registered)) {
            request.setAttribute("success", "Registration successful! Please login.");
        }
        String reset = request.getParameter("reset");
        if ("true".equals(reset)) {
            request.setAttribute("success", "Password reset successful! Please login with your new password.");
        }

        request.setAttribute("pageTitle", "Login");
        request.setAttribute("pageType", "auth");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Use service layer for authentication (includes AES + lockout)
        String[] errorHolder = new String[1];
        User user = userService.authenticateUser(email, password, errorHolder);

        if (user != null) {
            // Create session with user attributes
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getFullName());
            session.setAttribute("userRole", user.getRole());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes timeout

            // Redirect based on role
            if ("admin".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/donor/dashboard");
            }

        } else {
            request.setAttribute("error", errorHolder[0]);
            request.setAttribute("pageTitle", "Login");
            request.setAttribute("pageType", "auth");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
