<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--
    index.jsp - Landing Page
    This simply redirects visitors to the login page.
-->
<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
