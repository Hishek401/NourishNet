<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- login.jsp - Login Page with forgot password link --%>
<%@ include file="header.jsp" %>

    <div class="auth-container">

        <div class="auth-brand">
            <div class="brand-content">
                <div class="brand-icon">🍽️</div>
                <h1>NourishNet</h1>
                <p>Food Bank &amp; Donation Tracker</p>
                <div class="brand-tagline">
                    Making food donation simple, transparent, and impactful.
                </div>
            </div>
        </div>

        <div class="auth-form-section">
            <div class="auth-form-wrapper">
                <h2>Welcome Back</h2>
                <p class="auth-subtitle">Sign in to your account</p>

                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        ⚠️ <%= request.getAttribute("error") %>
                    </div>
                <% } %>

                <% if (request.getAttribute("success") != null) { %>
                    <div class="alert alert-success">
                        ✅ <%= request.getAttribute("success") %>
                    </div>
                <% } %>

                <form action="<%= ctx %>/login" method="POST" class="auth-form">
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" placeholder="Enter your email" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter your password" required>
                    </div>

                    <button type="submit" class="btn btn-primary btn-full">Sign In</button>
                </form>

                <div class="auth-footer">
                    <div style="margin-bottom: 15px; padding: 12px; border: 2px solid var(--primary); border-radius: 8px; background: var(--primary-bg);">
                        <a href="<%= ctx %>/forgot-password" style="color: var(--primary-dark); font-weight: 800; text-decoration: underline; font-size: 16px;">
                            🔑 FORGOT PASSWORD? RESET HERE
                        </a>
                    </div>
                    <p>Don't have an account? <a href="<%= ctx %>/register">Register here</a></p>
                </div>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>
