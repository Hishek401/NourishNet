package com.NourishNet.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * AuthFilter - Servlet Filter for Authentication and Authorization.
 * 
 * Intercepts ALL requests ("/*") and enforces:
 * 1. Unauthenticated users are redirected to login page
 * 2. Wrong-role users are redirected to their correct dashboard
 * 3. Public paths (login, register, css, about) are whitelisted
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Whitelist: public paths that don't require authentication
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check if user is logged in
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // Not logged in — redirect to login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // User is logged in — check role-based access
        String userRole = (String) session.getAttribute("userRole");

        // Admin trying to access donor pages
        if (path.startsWith("/donor") && "admin".equals(userRole)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/dashboard");
            return;
        }

        // Donor trying to access admin pages
        if (path.startsWith("/admin") && "donor".equals(userRole)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/donor/dashboard");
            return;
        }

        // All checks passed — continue with request
        chain.doFilter(request, response);
    }

    /**
     * Checks if the given path is public (does not require login).
     */
    private boolean isPublicPath(String path) {
        return path.equals("/") ||
               path.startsWith("/login") ||
               path.startsWith("/register") ||
               path.startsWith("/forgot-password") ||
               path.startsWith("/about") ||
               path.startsWith("/css/") ||
               path.endsWith(".css") ||
               path.endsWith(".js") ||
               path.endsWith(".png") ||
               path.endsWith(".jpg") ||
               path.endsWith(".ico");
    }

    @Override
    public void destroy() {
        // Filter cleanup
    }
}
