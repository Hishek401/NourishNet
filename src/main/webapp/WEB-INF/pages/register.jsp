<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- register.jsp - Registration Page --%>
<%@ include file="header.jsp" %>

    <div class="auth-container">
        <div class="auth-brand">
            <div class="brand-content">
                <div class="brand-icon">🍽️</div>
                <h1>NourishNet</h1>
                <p>Food Bank &amp; Donation Tracker</p>
                <div class="brand-tagline">
                    Join our community of donors making a difference in food security.
                </div>
            </div>
        </div>

        <div class="auth-form-section">
            <div class="auth-form-wrapper">
                <h2>Create Account</h2>
                <p class="auth-subtitle">Register as a donor</p>

                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-error">
                        ⚠️ <%= request.getAttribute("error") %>
                    </div>
                <% } %>

                <form action="<%= ctx %>/register" method="POST" class="auth-form">
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <input type="text" id="fullName" name="fullName" placeholder="Enter your full name" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" placeholder="Enter your email" required>
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" placeholder="Min 6 characters" required>
                        </div>
                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm password" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phone">Phone Number (10 digits)</label>
                        <input type="text" id="phone" name="phone" placeholder="e.g., 9812345678">
                    </div>

                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" placeholder="Enter your address">
                    </div>

                    <button type="submit" class="btn btn-primary btn-full">Create Account</button>
                </form>

                <div class="auth-footer">
                    <p>Already have an account? <a href="<%= ctx %>/login">Login here</a></p>
                </div>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>
