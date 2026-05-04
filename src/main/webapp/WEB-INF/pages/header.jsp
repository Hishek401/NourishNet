<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- header.jsp - Common header included in all pages via JSP include directive --%>
<%
    String pageTitle = (String) request.getAttribute("pageTitle");
    if (pageTitle == null) pageTitle = "NourishNet";
    String pageType = (String) request.getAttribute("pageType");
    if (pageType == null) pageType = "public";
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= pageTitle %> - NourishNet</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body class="<%= "auth".equals(pageType) ? "auth-page" : "dashboard-page" %>">

<% if ("admin".equals(pageType)) { %>
    <nav class="navbar navbar-admin">
        <div class="nav-brand">
            <span class="nav-icon">🍽️</span>
            <span class="nav-title">NourishNet</span>
            <span class="nav-badge">Admin</span>
        </div>
        <div class="nav-links">
            <span class="nav-user">👤 ${sessionScope.userName}</span>
            <a href="<%= ctx %>/admin/dashboard" class="nav-link active">Dashboard</a>
            <a href="<%= ctx %>/about" class="nav-link">About</a>
            <a href="<%= ctx %>/logout" class="nav-link nav-logout">Logout</a>
        </div>
    </nav>
<% } else if ("donor".equals(pageType)) { %>
    <nav class="navbar">
        <div class="nav-brand">
            <span class="nav-icon">🍽️</span>
            <span class="nav-title">NourishNet</span>
        </div>
        <div class="nav-links">
            <span class="nav-user">👤 Welcome, ${sessionScope.userName}</span>
            <a href="<%= ctx %>/donor/dashboard" class="nav-link">Dashboard</a>
            <a href="<%= ctx %>/donor/donate" class="nav-link">Donate Food</a>
            <a href="<%= ctx %>/donor/profile" class="nav-link">Profile</a>
            <a href="<%= ctx %>/about" class="nav-link">About</a>
            <a href="<%= ctx %>/logout" class="nav-link nav-logout">Logout</a>
        </div>
    </nav>
<% } else if ("public".equals(pageType)) { %>
    <nav class="navbar">
        <div class="nav-brand">
            <span class="nav-icon">🍽️</span>
            <span class="nav-title">NourishNet</span>
        </div>
        <div class="nav-links">
            <a href="<%= ctx %>/about" class="nav-link">About</a>
            <a href="<%= ctx %>/login" class="nav-link">Login</a>
            <a href="<%= ctx %>/register" class="nav-link">Register</a>
        </div>
    </nav>
<% } %>
