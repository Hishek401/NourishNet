package com.NourishNet.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

/**
 * AboutServlet - Shows the About page (extra page beyond CRUD).
 * 
 * URL: /about
 */
public class AboutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        jakarta.servlet.http.HttpSession session = request.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;
        
        request.setAttribute("pageTitle", "About Us");
        request.setAttribute("pageType", (userRole != null) ? userRole : "public");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/about.jsp");
        dispatcher.forward(request, response);
    }
}
