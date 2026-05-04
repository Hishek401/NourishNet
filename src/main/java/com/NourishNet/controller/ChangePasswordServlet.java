package com.NourishNet.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.RequestDispatcher;

import com.NourishNet.service.UserService;

/**
 * ChangePasswordServlet - Allows users to change their own password.
 * 
 * URL: /donor/change-password
 * GET  → Show change password form
 * POST → Process password change
 */
@WebServlet("/donor/change-password")
public class ChangePasswordServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("pageTitle", "Change Password");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/change-password.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        int userId = (int) session.getAttribute("userId");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        String error = userService.changePassword(userId, oldPassword, newPassword, confirmPassword);

        if (error == null) {
            request.setAttribute("success", "Password changed successfully!");
        } else {
            request.setAttribute("error", error);
        }

        request.setAttribute("pageTitle", "Change Password");
        request.setAttribute("pageType", "donor");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/change-password.jsp");
        dispatcher.forward(request, response);
    }
}
