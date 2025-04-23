<%-- 
    Document   : shop_dashboard
    Created on : 23 Apr 2025, 1:42:51 pm
    Author     : local_user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="Management" scope="request"/>
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
                        <h4 class="mb-3">AIB Dashboard</h4>
                        <nav class="nav flex-column">
                            <a class="nav-link ${param.tab == 'reserving' ? 'active' : ''}" 
                               href="?tab=reserving">Reservation Analytics</a>
                            <a class="nav-link ${param.tab == 'consumption' ? 'active' : ''}" 
                               href="?tab=consumption">Consumption Analytics</a>
                            <a class="nav-link ${param.tab == 'employee' ? 'active' : ''}" 
                               href="?tab=employee">Employee</a>
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
                        <h3>Reservation Analytics</h3>
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Reservation Record</h5>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Region</th>
                                            <th>Season</th>
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


                    <!-- Consumption Content -->
                    <c:if test="${param.tab == 'consumption'}">
                        <h3>Consumption Analytics</h3>
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Consumption Record</h5>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Region</th>
                                            <th>Shop</th>
                                            <th>Date</th>
                                            <th>Season</th>
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

                    <!-- Employee list -->
                    <c:if test="${param.tab == 'employee'}">
                        <h3>Employee</h3>
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Employee List</h5>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Region</th>
                                            <th>Location</th>
                                            <th>Name</th>
                                            <th>Role</th>
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
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
