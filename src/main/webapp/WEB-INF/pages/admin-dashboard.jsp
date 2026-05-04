<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.NourishNet.model.User" %>
<%@ page import="com.NourishNet.model.Donation" %>
<%@ page import="com.NourishNet.model.Recipient" %>
<%-- admin-dashboard.jsp - Admin Dashboard with full CRUD, search, unlock --%>
<%@ include file="header.jsp" %>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Admin Dashboard</h1>
        </div>

        <%-- Stats Cards --%>
        <div class="stats-grid stats-grid-5">
            <div class="stat-card stat-total">
                <div class="stat-icon">👥</div>
                <div class="stat-info">
                    <h3>${totalDonors}</h3>
                    <p>Total Donors</p>
                </div>
            </div>
            <div class="stat-card stat-donations">
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
            <div class="stat-card stat-rejected">
                <div class="stat-icon">❌</div>
                <div class="stat-info">
                    <h3>${rejectedCount}</h3>
                    <p>Rejected</p>
                </div>
            </div>
        </div>



        <%-- All Donations Table with Search --%>
        <div class="table-container">
            <div class="table-header-row">
                <h2>All Donations</h2>
                <form action="<%= ctx %>/admin/dashboard" method="GET" class="search-form">
                    <input type="text" name="search" placeholder="Search by food, category, or donor..."
                           value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>"
                           class="search-input">
                    <button type="submit" class="btn btn-primary btn-sm">Search</button>
                    <% if (request.getAttribute("searchQuery") != null) { %>
                        <a href="<%= ctx %>/admin/dashboard" class="btn btn-secondary btn-sm">Clear</a>
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
                            <th>Donor</th>
                            <th>Food Item</th>
                            <th>Qty</th>
                            <th>Category</th>
                            <th>Expiry</th>
                            <th>Recipient</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int dCount = 1;
                            for (Donation d : donations) {
                        %>
                        <tr>
                            <td><%= dCount++ %></td>
                            <td><%= d.getDonorName() %></td>
                            <td><%= d.getFoodItem() %></td>
                            <td><%= d.getQuantity() %> <%= d.getUnit() %></td>
                            <td><%= d.getCategory() %></td>
                            <td><%= d.getExpiryDate() != null ? d.getExpiryDate() : "N/A" %></td>
                            <td><%= d.getRecipientName() != null ? d.getRecipientName() : "—" %></td>
                            <td>
                                <span class="badge badge-<%= d.getStatus().toLowerCase() %>">
                                    <%= d.getStatus() %>
                                </span>
                            </td>
                            <td class="actions-cell">
                                <% if ("Pending".equals(d.getStatus())) { %>
                                    <form method="POST" action="<%= ctx %>/admin/dashboard">
                                        <input type="hidden" name="action" value="approve">
                                        <input type="hidden" name="donationId" value="<%= d.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-approve">✓</button>
                                    </form>
                                    <form method="POST" action="<%= ctx %>/admin/dashboard">
                                        <input type="hidden" name="action" value="reject">
                                        <input type="hidden" name="donationId" value="<%= d.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-reject">✗</button>
                                    </form>
                                <% } %>
                                    <a href="<%= ctx %>/admin/dashboard?editId=<%= d.getId() %>" class="btn btn-sm btn-secondary">Edit</a>
                                    <form method="POST" action="<%= ctx %>/admin/dashboard"
                                          onsubmit="return confirm('Delete this donation?');">
                                        <input type="hidden" name="action" value="deleteDonation">
                                        <input type="hidden" name="donationId" value="<%= d.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-delete">🗑</button>
                                    </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="empty-state">
                    <div class="empty-icon">📦</div>
                    <h3>No donations found</h3>
                    <p>Donations will appear here once donors start submitting.</p>
                </div>
            <% } %>
        </div>

        <%-- Registered Donors Table with Unlock --%>
        <div class="table-container">
            <div class="table-header-row">
                <h2>Registered Donors</h2>
                <form action="<%= ctx %>/admin/dashboard" method="GET" class="search-form">
                    <input type="text" name="donorSearch" placeholder="Search donors by name or email..."
                           value="<%= request.getAttribute("donorSearchQuery") != null ? request.getAttribute("donorSearchQuery") : "" %>"
                           class="search-input">
                    <button type="submit" class="btn btn-primary btn-sm">Search</button>
                    <% if (request.getAttribute("donorSearchQuery") != null) { %>
                        <a href="<%= ctx %>/admin/dashboard" class="btn btn-secondary btn-sm">Clear</a>
                    <% } %>
                </form>
            </div>
            <%
                List<User> donorList = (List<User>) request.getAttribute("donors");
            %>
            <% if (donorList != null && !donorList.isEmpty()) { %>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Address</th>
                            <th>Status</th>
                            <th>Joined</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int uCount = 1;
                            for (User u : donorList) {
                        %>
                        <tr>
                            <td><%= uCount++ %></td>
                            <td><%= u.getFullName() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><%= u.getPhone() != null ? u.getPhone() : "N/A" %></td>
                            <td><%= u.getAddress() != null ? u.getAddress() : "N/A" %></td>
                            <td>
                                <% if (u.getIsLocked()) { %>
                                    <span class="badge badge-rejected">Locked</span>
                                <% } else { %>
                                    <span class="badge badge-approved">Active</span>
                                <% } %>
                            </td>
                            <td><%= u.getCreatedAt() %></td>
                            <td class="actions-cell">
                                <% if (u.getIsLocked()) { %>
                                    <form method="POST" action="<%= ctx %>/admin/dashboard">
                                        <input type="hidden" name="action" value="unlockAccount">
                                        <input type="hidden" name="userId" value="<%= u.getId() %>">
                                        <button type="submit" class="btn btn-sm btn-approve">Unlock</button>
                                    </form>
                                <% } %>
                                <form method="POST" action="<%= ctx %>/admin/dashboard"
                                      onsubmit="return confirm('Delete this donor and all their donations?');">
                                    <input type="hidden" name="action" value="deleteDonor">
                                    <input type="hidden" name="donorId" value="<%= u.getId() %>">
                                    <button type="submit" class="btn btn-sm btn-delete">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="empty-state">
                    <div class="empty-icon">👥</div>
                    <h3>No donors registered yet</h3>
                </div>
            <% } %>
        </div>
    </div>

<%@ include file="footer.jsp" %>
