<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- profile.jsp - User Profile Page (uses jsp:useBean and jsp:getProperty) --%>
<%@ include file="common/header.jsp" %>

    <%-- Using jsp:useBean and jsp:getProperty as required by Week 5 --%>
    <jsp:useBean id="profileUser" class="com.NourishNet.model.User" scope="request"/>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>My Profile</h1>
            <a href="<%= ctx %>/donor/change-password" class="btn btn-secondary">🔑 Change Password</a>
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

        <%-- Display profile info using jsp:getProperty --%>
        <div class="form-container">
            <div class="profile-info">
                <p><strong>Email:</strong> <jsp:getProperty name="profileUser" property="email"/></p>
                <p><strong>Role:</strong> <jsp:getProperty name="profileUser" property="role"/></p>
                <p><strong>Member Since:</strong> <jsp:getProperty name="profileUser" property="createdAt"/></p>
            </div>

            <h3 style="margin-top: 20px; margin-bottom: 16px;">Edit Profile</h3>

            <form action="<%= ctx %>/donor/profile" method="POST" class="donation-form">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName"
                           value="<jsp:getProperty name="profileUser" property="fullName"/>" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="text" id="phone" name="phone"
                           value="<jsp:getProperty name="profileUser" property="phone"/>">
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address"
                           value="<jsp:getProperty name="profileUser" property="address"/>">
                </div>

                <button type="submit" class="btn btn-primary btn-full">Update Profile</button>
            </form>
        </div>
    </div>

<%@ include file="common/footer.jsp" %>
