<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.NourishNet.model.Donation" %>
<%@ page import="com.NourishNet.model.Recipient" %>
<%-- submit-donation.jsp - Donation Submission Form with recipient selection --%>
<%@ include file="header.jsp" %>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Submit a Donation</h1>
            <a href="<%= ctx %>/donor/dashboard" class="btn btn-secondary">← Back to Dashboard</a>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                ⚠️ <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <div class="form-container">
            <form action="<%= ctx %>/donor/donate" method="POST" class="donation-form">
                <div class="form-group">
                    <label for="foodItem">Food Item *</label>
                    <input type="text" id="foodItem" name="foodItem" placeholder="e.g., Rice, Bread, Canned Beans" required>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="quantity">Quantity *</label>
                        <input type="number" id="quantity" name="quantity" placeholder="e.g., 5" min="1" required>
                    </div>
                    <div class="form-group">
                        <label for="unit">Unit</label>
                        <select id="unit" name="unit">
                            <option value="kg">Kilograms (kg)</option>
                            <option value="liters">Liters</option>
                            <option value="packets">Packets</option>
                            <option value="pieces">Pieces</option>
                            <option value="boxes">Boxes</option>
                            <option value="cans">Cans</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="category">Category</label>
                        <select id="category" name="category">
                            <option value="Grains">Grains &amp; Cereals</option>
                            <option value="Dairy">Dairy Products</option>
                            <option value="Fruits">Fruits &amp; Vegetables</option>
                            <option value="Canned">Canned Food</option>
                            <option value="Beverages">Beverages</option>
                            <option value="Snacks">Snacks</option>
                            <option value="Other">Other</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="expiryDate">Expiry Date</label>
                        <input type="date" id="expiryDate" name="expiryDate">
                    </div>
                </div>

                <div class="form-group">
                    <label for="recipientId">Donate To (Recipient)</label>
                    <select id="recipientId" name="recipientId">
                        <option value="">-- Select Recipient (Optional) --</option>
                        <%
                            List<Recipient> recipients = (List<Recipient>) request.getAttribute("recipients");
                            if (recipients != null) {
                                for (Recipient r : recipients) {
                        %>
                            <option value="<%= r.getId() %>"><%= r.getName() %> (<%= r.getOrganizationType() %>)</option>
                        <%
                                }
                            }
                        %>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary btn-full">Submit Donation</button>
            </form>
        </div>
    </div>

<%@ include file="footer.jsp" %>
