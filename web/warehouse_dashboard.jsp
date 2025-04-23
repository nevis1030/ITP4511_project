<%-- 
    Document   : shop_dashboard
    Created on : 23 Apr 2025, 1:42:51 pm
    Author     : local_user
--%>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="pageTitle" value="Warehouse Staff" scope="request"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/module/title.jsp"%>
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
                    <div class="d-flex flex-column p-3" style="height: 100%">
                        <h4 class="mb-3">
                            <a href="?" class="text-decoration-none text-reset">AIB Dashboard</a>
                        </h4>
                        <nav class="nav flex-column">
                            <a class="nav-link ${param.tab == 'reserving' ? 'active' : ''}" 
                               href="?tab=reserving">Reserving Fruit</a>
                            <a class="nav-link ${param.tab == 'stock' ? 'active' : ''}" 
                               href="?tab=stock">Stock Level</a>
                            <a class="nav-link ${param.tab == 'account' ? 'active' : ''}" 
                               href="?tab=account">Account</a>
                        </nav>
                        <a class="mt-auto btn btn-primary" href="index.jsp">Logout</a>  
                    </div>
                </div>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 p-4">
                    <c:if test="${empty param.tab}">
                        <div class="text-center mt-5">
                            <h2>Welcome to AIB Warehouse Dashboard</h2>
                            <p class="lead">Select a tab from the sidebar to get started</p>
                            <div class="card mt-4">
                                <div class="card-body">
                                    <h5>Quick Stats</h5>
                                    <p>Total Reservations Today: 15</p>
                                    <p>Pending Borrow Requests: 3</p>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <!-- Reserving Fruit Content -->
                    <c:if test="${param.tab == 'reserving'}">
                        <h3>Reserving Fruit</h3>
                        <ul class="nav nav-tabs mb-4">
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'request' ? 'active' : ''}" 
                                   href="?tab=reserving&subtab=request">Reserve Request</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'record' ? 'active' : ''}" 
                                   href="?tab=reserving&subtab=record">Reserve Record</a>
                            </li>
                        </ul>

                        <c:choose>
                            <c:when test="${param.subtab == 'request'}">
                                <!-- Reserve Request Form -->
                                <div class="card">
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Date</th>
                                                    <th>From</th>
                                                    <th>Fruit</th>
                                                    <th>Quantity</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Add dynamic data here -->
                                            </tbody>
                                    </div>
                                </div>
                                </table>
                            </c:when>
                            <c:when test="${param.subtab == 'record'}">
                                <!-- Reservation Records Table -->
                                <div class="card">
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>Date</th>
                                                    <th>Fruit</th>
                                                    <th>Quantity</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Add dynamic data here -->
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                
                            </c:when>
                            <c:otherwise>
                                <p>Select a tab to begin</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>


                    <!-- Stock Level Content -->
                    <c:if test="${param.tab == 'stock'}">
                        <h3>Stock Level</h3>
                        <div class="card">
                            <div class="card-body">
                                <!-- Borrow Records Table -->
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Fruit</th>
                                            <th>Quantity</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- Add dynamic data here -->
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>

                    <!-- Account Content -->
                    <c:if test="${param.tab == 'account'}">
                        <h3>Account Settings</h3>
                        <ul class="nav nav-tabs mb-4">
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'password' ? 'active' : ''}" 
                                   href="?tab=account&subtab=password">Change Password</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'details' ? 'active' : ''}" 
                                   href="?tab=account&subtab=details">Change/View Details</a>
                            </li>
                        </ul>

                        <c:choose>
                            <c:when test="${param.subtab == 'password'}">
                                <!-- Password Change Form -->
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Change Password</h5>
                                        <!-- Add password form here -->
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${param.subtab == 'details'}">
                                <!-- Account Details Form -->
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">Account Details</h5>
                                        <!-- Add details form here -->
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p>Select a tab to begin</p>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
