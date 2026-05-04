<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- about.jsp - About Page (Extra page beyond core CRUD) --%>
<%@ include file="header.jsp" %>

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>About NourishNet</h1>
        </div>

        <div class="form-container" style="max-width: 900px;">
            <div class="about-section">
                <div class="brand-icon" style="font-size: 48px; text-align: center; margin-bottom: 16px;">🍽️</div>
                <h2 style="text-align: center; margin-bottom: 24px;">Food Bank &amp; Donation Tracker System</h2>

                <p style="margin-bottom: 16px; line-height: 1.8;">
                    <strong>NourishNet</strong> is a web-based food bank and donation tracking system designed
                    to connect generous donors with food banks, shelters, and community kitchens across Nepal.
                    Our mission is to reduce food waste and ensure that surplus food reaches those who need it most.
                </p>

                <h3 style="margin-top: 24px; margin-bottom: 12px;">🎯 Our Mission</h3>
                <p style="margin-bottom: 16px; line-height: 1.8;">
                    To create a transparent, efficient platform for food donation management that bridges the gap
                    between food surplus and food insecurity in our communities.
                </p>

                <h3 style="margin-top: 24px; margin-bottom: 12px;">✨ Key Features</h3>
                <ul style="margin-left: 20px; margin-bottom: 16px; line-height: 2;">
                    <li>Easy food donation submission with category tracking</li>
                    <li>Real-time donation status tracking (Pending, Approved, Rejected)</li>
                    <li>Admin dashboard for managing donations and donors</li>
                    <li>Recipient organization management</li>
                    <li>Secure user authentication with AES encryption</li>
                    <li>Search and filter donations</li>
                </ul>

                <h3 style="margin-top: 24px; margin-bottom: 12px;">🛠️ Technology Stack</h3>
                <ul style="margin-left: 20px; margin-bottom: 16px; line-height: 2;">
                    <li><strong>Backend:</strong> Java Servlets (Jakarta EE), JSP</li>
                    <li><strong>Database:</strong> MySQL with JDBC</li>
                    <li><strong>Server:</strong> Apache Tomcat 10.x</li>
                    <li><strong>Security:</strong> AES Encryption, Session Management</li>
                    <li><strong>Architecture:</strong> MVC Pattern with Service Layer</li>
                </ul>

                <h3 style="margin-top: 24px; margin-bottom: 12px;">📧 Contact</h3>
                <p style="line-height: 1.8;">
                    Email: info@nourishnet.com<br>
                    Location: Islington College, Kamal Marg, Kathmandu, Nepal<br>
                    Phone: +977-9800000000
                </p>
            </div>
        </div>
    </div>

<%@ include file="footer.jsp" %>
