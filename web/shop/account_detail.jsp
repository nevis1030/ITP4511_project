<%-- 
    Document   : account_detail
    Created on : 26 Apr 2025, 2:27:49 pm
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
                            <h5>Account Detail</h5>
                            <form>
                                User ID: <input type="text" name="userId" value="${sessionScope.user.userId}" disabled="true"><br><br>
                                Name: <input type="text" name="displayName" value="${sessionScope.user.displayName}" disabled="true"><br><br>
                                Role: 
                                <select name="role" class="form-select" disabled="true">
                                    <option value="shopStaff" 
                                            ${sessionScope.user.role == 0 ? 'selected' : ''}>
                                        Shop Staff
                                    </option>
                                    <option value="warehouseStaff" 
                                            ${sessionScope.user.role == 1 ? 'selected' : ''}>
                                        Warehouse Staff
                                    </option>
                                    <option value="seniorManager" 
                                            ${sessionScope.user.role == 2 ? 'selected' : ''}>
                                        Senior Manager
                                    </option>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>