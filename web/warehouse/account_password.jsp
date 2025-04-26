<%-- 
    Document   : account_password
    Created on : 26 Apr 2025, 2:26:47 pm
    Author     : local_user
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="pageTitle" value="Management" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="title.jsp"%>
        <style>
            .sidebar {
                height: 100vh;
                background-color: #f8f9fa;
                border-right: 1px solid #dee2e6;
            }
            .nav-link.active {
                background-color: #e9ecef;
                font-weight: 500;
            }
        </style>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 col-lg-2 sidebar">
                    <%@include file="sidebar.jsp"%>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 p-4">
                    <%@include file="account_tab.jsp"%>

                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Change Password</h5>
                            <form action="password_change" method="post">
                                <%
                                    if (request.getAttribute("error") != null && request.getAttribute("error") == "access_denied"){
                                %>
                                <div class="alert alert-danger">
                                    Incorrect password
                                </div>
                                <%
                                    }else if (request.getAttribute("error") != null && request.getAttribute("error") == "success"){
                                %>
                                <div class="alert alert-warning">
                                    Password changed
                                </div>   
                                <%
                                    }
                                %>
                                <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                                <input type="hidden" name="old_password" value="${sessionScope.user.password}">
                                Old password: <input name="validate_password" required/><br><br>
                                New password: <input name="new_password" required/><br><br>
                                <input class="btn btn-primary" type="submit" value="submit">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>