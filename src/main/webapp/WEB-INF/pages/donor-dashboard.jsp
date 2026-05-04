<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.NourishNet.model.Donation" %>
<%-- donor-dashboard.jsp - Donor Dashboard with search and cancel --%>
<%@ include file="common/header.jsp" %>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Donor Dashboard</h1>
            <a href="<%= ctx %>/donor/donate" class="btn btn-primary">+ Submit Donation</a>
        </div>

        <% if ("true".equals(request.getParameter("donated"))) { %>
            <div class="alert alert-success">
                ✅ Your donation has been submitted successfully! It is pending admin approval.
            </div>
        <% } %>

        <%-- Stats Cards --%>
        <div class="stats-grid">
            <div class="stat-card stat-total">
                <div class="stat-icon">📦</div>
                <div class="stat-info">
                    <h3>${totalDonations}</h3>
                    <p>Total Donations</p>
                </div>
            </div>
            <div class="stat-card stat-approved">
                <div class="stat-icon">✅</div>
                <div class="stat-info">
                    <h3>${approvedCount}</h3>
                    <p>Approved</p>
                </div>
            </div>
            <div class="stat-card stat-pending">
                <div class="stat-icon">⏳</div>
                <div class="stat-info">
                    <h3>${pendingCount}</h3>
                    <p>Pending</p>
                </div>
            </div>
        </div>

        <%-- Donations Table with Search --%>
        <div class="table-container">
            <div class="table-header-row">
                <h2>My Donations</h2>
                <form action="<%= ctx %>/donor/dashboard" method="GET" class="search-form">
                    <input type="text" name="search" placeholder="Search by food item or category..."
                           value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>"
                           class="search-input">
                    <button type="submit" class="btn btn-primary btn-sm">Search</button>
                    <% if (request.getAttribute("searchQuery") != null) { %>
                        <a href="<%= ctx %>/donor/dashboard" class="btn btn-secondary btn-sm">Clear</a>
                    <% } %>
                </form>
            </div>

            <%
                List<Donation> donations = (List<Donation>) request.getAttribute("donations");
            %>

            <% if (donations != null && !donations.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Food Item</th>
                            <th>Quantity</th>
                            <th>Category</th>
                            <th>Expiry Date</th>
                            <th>Status</th>
                            <th>Submitted On</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 1;
                            for (Donation d : donations) {
                        %>
                        <tr>
                            <td><%= count++ %></td>
                            <td><%= d.getFoodItem() %></td>
                            <td><%= d.getQuantity() %> <%= d.getUnit() %></td>
                            <td><%= d.getCategory() %></td>
                            <td><%= d.getExpiryDate() != null ? d.getExpiryDate() : "N/A" %></td>
                            <td>
                                <span class="badge badge-<%= d.getStatus().toLowerCase() %>">
                                    <%= d.getStatus() %>
                                </span>
                            </td>
                            <td><%= d.getCreatedAt() %></td>
                            <td>
                                <% if ("Pending".equals(d.getStatus())) { %>
                                    <form method="POST" action="<%= ctx %>/donor/dashboard"
                                          onsubmit="return confirm('Cancel this donation?');">
                                        <input type="hidden" name="action" value="cancel">
                                        <input type="hidden" name="donationId" value="<%= d.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-reject">Cancel</button>
                                    </form>
                                <% } else { %>
                                    <span class="text-muted">—</span>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="empty-state">
                    <div class="empty-icon">🍽️</div>
                    <h3>No donations yet</h3>
                    <p>Start making a difference by donating food items!</p>
                    <a href="<%= ctx %>/donor/donate" class="btn btn-primary">Submit Your First Donation</a>
                </div>
            <% } %>
        </div>
    </div>

<%@ include file="common/footer.jsp" %>
