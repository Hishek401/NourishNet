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
 * ForgotPasswordServlet - Handles password reset for users who forgot their password.
 * 
 * URL: /forgot-password
 * GET  → Show forgot password form
 * POST → Process password reset
 */
@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pageTitle", "Forgot Password");
        request.setAttribute("pageType", "auth");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/forgot-password.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String error = userService.resetPassword(email, newPassword, confirmPassword);

        if (error == null) {
            response.sendRedirect(request.getContextPath() + "/login?reset=true");
        } else {
            request.setAttribute("error", error);
            request.setAttribute("pageTitle", "Forgot Password");
            request.setAttribute("pageType", "auth");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/forgot-password.jsp");
            dispatcher.forward(request, response);
        }
    }
}
