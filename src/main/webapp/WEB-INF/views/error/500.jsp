<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%-- 500.jsp - Internal Server Error Page --%>
<%@ include file="../common/header.jsp" %>

    <div class="dashboard-container">
        <div class="empty-state">
            <div class="empty-icon">⚠️</div>
            <h1>500 - Server Error</h1>
            <p>Something went wrong on our end. Please try again later.</p>
            <a href="<%= ctx %>/login" class="btn btn-primary">Go to Home</a>
        </div>
    </div>

<%@ include file="../common/footer.jsp" %>
