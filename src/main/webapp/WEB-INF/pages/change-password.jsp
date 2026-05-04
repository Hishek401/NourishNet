<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- change-password.jsp - Change Password Page --%>
<%@ include file="common/header.jsp" %>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Change Password</h1>
            <a href="<%= ctx %>/donor/profile" class="btn btn-secondary">← Back to Profile</a>
        </div>

        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                ✅ <%= request.getAttribute("success") %>
            </div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                ⚠️ <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <div class="form-container">
            <form action="<%= ctx %>/donor/change-password" method="POST" class="donation-form">
                <div class="form-group">
                    <label for="oldPassword">Current Password</label>
                    <input type="password" id="oldPassword" name="oldPassword" placeholder="Enter current password" required>
                </div>

                <div class="form-group">
                    <label for="newPassword">New Password</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Min 6 characters" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm new password" required>
                </div>

                <button type="submit" class="btn btn-primary btn-full">Change Password</button>
            </form>
        </div>
    </div>

<%@ include file="common/footer.jsp" %>
