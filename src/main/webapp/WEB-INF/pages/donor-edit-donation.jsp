<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.NourishNet.model.Donation" %>
<%@ page import="com.NourishNet.model.Recipient" %>
<%-- donor-edit-donation.jsp - Donor page to edit their donation --%>
<%@ include file="header.jsp" %>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Edit Donation</h1>
            <a href="<%= ctx %>/donor/dashboard" class="btn btn-secondary">← Back to Dashboard</a>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                ⚠️ <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <div class="form-container">
            <%
                Donation d = (Donation) request.getAttribute("donation");
                List<Recipient> recipients = (List<Recipient>) request.getAttribute("recipients");
            %>
            <form action="<%= ctx %>/donor/edit-donation" method="POST" class="donation-form">
                <input type="hidden" name="donationId" value="<%= d.getId() %>">

                <div class="form-group">
                    <label for="foodItem">Food Item *</label>
                    <input type="text" id="foodItem" name="foodItem" value="<%= d.getFoodItem() %>" required>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="quantity">Quantity *</label>
                        <input type="number" id="quantity" name="quantity" value="<%= d.getQuantity() %>" min="1" required>
                    </div>
                    <div class="form-group">
                        <label for="unit">Unit</label>
                        <select id="unit" name="unit">
                            <option value="kg" <%= "kg".equals(d.getUnit()) ? "selected" : "" %>>Kilograms (kg)</option>
                            <option value="liters" <%= "liters".equals(d.getUnit()) ? "selected" : "" %>>Liters</option>
                            <option value="packets" <%= "packets".equals(d.getUnit()) ? "selected" : "" %>>Packets</option>
                            <option value="pieces" <%= "pieces".equals(d.getUnit()) ? "selected" : "" %>>Pieces</option>
                            <option value="boxes" <%= "boxes".equals(d.getUnit()) ? "selected" : "" %>>Boxes</option>
                            <option value="cans" <%= "cans".equals(d.getUnit()) ? "selected" : "" %>>Cans</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="category">Category</label>
                        <select id="category" name="category">
                            <option value="Grains" <%= "Grains".equals(d.getCategory()) ? "selected" : "" %>>Grains &amp; Cereals</option>
                            <option value="Dairy" <%= "Dairy".equals(d.getCategory()) ? "selected" : "" %>>Dairy Products</option>
                            <option value="Fruits" <%= "Fruits".equals(d.getCategory()) ? "selected" : "" %>>Fruits &amp; Vegetables</option>
                            <option value="Canned" <%= "Canned".equals(d.getCategory()) ? "selected" : "" %>>Canned Food</option>
                            <option value="Beverages" <%= "Beverages".equals(d.getCategory()) ? "selected" : "" %>>Beverages</option>
                            <option value="Snacks" <%= "Snacks".equals(d.getCategory()) ? "selected" : "" %>>Snacks</option>
                            <option value="Other" <%= "Other".equals(d.getCategory()) ? "selected" : "" %>>Other</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="expiryDate">Expiry Date</label>
                        <input type="date" id="expiryDate" name="expiryDate" value="<%= d.getExpiryDate() != null ? d.getExpiryDate() : "" %>">
                    </div>
                </div>

                <div class="form-group">
                    <label for="recipientId">Donate To (Recipient)</label>
                    <select id="recipientId" name="recipientId">
                        <option value="">-- Select Recipient (Optional) --</option>
                        <% if (recipients != null) { for (Recipient r : recipients) { %>
                            <option value="<%= r.getId() %>" <%= r.getId() == d.getRecipientId() ? "selected" : "" %>>
                                <%= r.getName() %> (<%= r.getOrganizationType() %>)
                            </option>
                        <% } } %>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary btn-full">Save Changes</button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>
