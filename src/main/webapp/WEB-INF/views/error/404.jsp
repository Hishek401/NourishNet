<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 404.jsp - Page Not Found Error Page --%>
<%@ include file="../common/header.jsp" %>

    <div class="dashboard-container">
        <div class="empty-state">
            <div class="empty-icon">🔍</div>
            <h1>404 - Page Not Found</h1>
            <p>The page you are looking for does not exist or has been moved.</p>
            <a href="<%= ctx %>/login" class="btn btn-primary">Go to Home</a>
        </div>
    </div>

<%@ include file="../common/footer.jsp" %>
