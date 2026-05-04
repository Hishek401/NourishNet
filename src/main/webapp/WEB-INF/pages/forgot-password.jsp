<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- forgot-password.jsp - Password Reset Page --%>
<%@ include file="header.jsp" %>

    <div class="auth-container">
        <div class="auth-brand">
            <div class="brand-content">
                <div class="brand-icon">🔑</div>
                <h1>NourishNet</h1>
                <p>Food Bank &amp; Donation Tracker</p>
                <div class="brand-tagline">
                    Reset your password to regain access to your account.
                </div>
            </div>
        </div>

        <div class="auth-form-section">
            <div class="auth-form-wrapper">
                <h2>Reset Password</h2>
                <p class="auth-subtitle">Enter your email and new password</p>

                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        ⚠️ <%= request.getAttribute("error") %>
                    </div>
                <% } %>

                <form action="<%= ctx %>/forgot-password" method="POST" class="auth-form">
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" placeholder="Enter your registered email" required>
                    </div>

                    <div class="form-group">
                        <label for="newPassword">New Password</label>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Min 6 characters" required>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Confirm New Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required>
                    </div>

                    <button type="submit" class="btn btn-primary btn-full">Reset Password</button>
                </form>

                <div class="auth-footer">
                    <p>Remembered your password? <a href="<%= ctx %>/login">Login here</a></p>
                </div>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>
