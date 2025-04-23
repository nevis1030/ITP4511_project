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
<c:set var="pageTitle" value="Shop Staff" scope="request"/>
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
                            <a class="nav-link ${param.tab == 'borrowing' ? 'active' : ''}" 
                               href="?tab=borrowing">Borrowing Fruit</a>
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
                            <h2>Welcome to AIB Bakery Dashboard</h2>
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
                                        <h5 class="card-title">New Reservation</h5>
                                        <%-- System-generated dates --%>
                                        <%
                                            LocalDate localStartDate = LocalDate.now();
                                            LocalDate localEndDate = localStartDate.plusDays(14);
                                            Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                            Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                        %>

                                        <c:set var="startDate" value="<%= startDate %>" />
                                        <c:set var="endDate" value="<%= endDate %>" />

                                        <form action="${pageContext.request.contextPath}/reservations/create" method="post">

                                            <%-- Hidden date fields --%>
                                            <input type="hidden" name="startDate" value="${startDate}">
                                            <input type="hidden" name="endDate" value="${endDate}">

                                            <div class="row g-3">
                                                <!-- Date Display -->
                                                <div class="col-12">
                                                    <div class="alert alert-info">
                                                        <strong>Reservation Period:</strong><br>
                                                        Start Date: <fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" /><br>
                                                        End Date: <fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />
                                                    </div>
                                                </div>

                                                <!-- Fruit Selection -->
                                                <div class="col-md-6">
                                                    <label for="fruitId" class="form-label">Select Fruit <span class="text-danger">*</span></label>
                                                    <select class="form-select" id="fruitId" name="fruitId" required>
                                                        <option value="">-- Select Fruit --</option>
                                                        <c:forEach items="${availableFruits}" var="fruit">
                                                            <option value="${fruit.fruitId}" ${param.fruitId == fruit.fruitId ? 'selected' : ''}>
                                                                ${fruit.name} (Source: ${fruit.sourceCity})
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Quantity Input -->
                                                <div class="col-md-6">
                                                    <label for="quantity" class="form-label">Quantity <span class="text-danger">*</span></label>
                                                    <input type="number" class="form-control" id="quantity" 
                                                           name="quantity" min="1" max="1000" 
                                                           value="${param.quantity}" required>
                                                    <div class="form-text">Enter quantity between 1-1000</div>
                                                </div>

                                                <!-- Submit Button -->
                                                <div class="col-12">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="bi bi-send me-2"></i>Submit Reservation
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
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
                                                <tr>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxx</td>
                                                    <td>2</td>
                                                    <td>Pending</td>
                                                </tr>
                                                <tr>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxx</td>
                                                    <td>2</td>
                                                    <td>Approved</td>
                                                </tr>
                                                <tr>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxx</td>
                                                    <td>2</td>
                                                    <td>Denied</td>
                                                </tr>
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

                    <!-- Borrowing Fruit Content -->
                    <c:if test="${param.tab == 'borrowing'}">
                        <h3>Borrowing Fruit</h3>
                        <ul class="nav nav-tabs mb-4">
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'pending' ? 'active' : ''}" 
                                   href="?tab=borrowing&subtab=pending">Pending Approval</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'approval-record' ? 'active' : ''}" 
                                   href="?tab=borrowing&subtab=approval-record">Approval Record</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'borrow-request' ? 'active' : ''}" 
                                   href="?tab=borrowing&subtab=borrow-request">Borrow Request</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${param.subtab == 'borrow-record' ? 'active' : ''}" 
                                   href="?tab=borrowing&subtab=borrow-record">Borrow Record</a>
                            </li>
                        </ul>

                        <c:choose>
                            <c:when test="${param.subtab == 'pending'}">
                                <!-- Pending Records Table -->
                                <div class="card">
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Date</th>
                                                    <th>To</th>
                                                    <th>Fruit</th>
                                                    <th>Quantity</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td>
                                                    <td>
                                                        <button type="submit" class="btn btn-primary">Approve</button>
                                                        <button type="submit" class="btn btn-danger">Deny</button>
                                                    </td>
                                                </tr>
                                                <!-- Add dynamic data here -->
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                            </c:when>
                            <c:when test="${param.subtab == 'approval-record'}">
                                <!-- approval Records Table -->
                                <div class="card">
                                    <div class="card-body">
                                        <table class="table">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Date</th>
                                                    <th>To</th>
                                                    <th>Fruit</th>
                                                    <th>Quantity</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td> 
                                                    <td>Approved</td> 
                                                </tr>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td> 
                                                    <td>Denied</td> 
                                                </tr>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td> 
                                                    <td>Pending</td> 
                                                </tr>
                                                <!-- Add dynamic data here -->
                                            </tbody>
                                        </table>  
                                    </div>
                                </div>

                            </c:when>
                            <c:when test="${param.subtab == 'borrow-request'}">
                                <!-- Reserve Request Form -->
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">New Borrow</h5>
                                        <%-- System-generated dates --%>
                                        <%
                                            LocalDate localStartDate = LocalDate.now();
                                            Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                                        %>

                                        <c:set var="startDate" value="<%= startDate %>" />

                                        <form action="${pageContext.request.contextPath}/borrow/create" method="post">

                                            <%-- Hidden date fields --%>
                                            <input type="hidden" name="startDate" value="${startDate}">

                                            <div class="row g-3">
                                                <!-- Date Display -->
                                                <div class="col-12">
                                                    <div class="alert alert-info">
                                                        <strong>Borrow Date:</strong><br>
                                                        Date: <fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />
                                                    </div>
                                                </div>

                                                <!-- Shop Selection -->
                                                <div class="col-md-6">
                                                    <label for="shopId" class="form-label">Select Shop <span class="text-danger">*</span></label>
                                                    <select class="form-select" id="shopId" name="shopId" required>
                                                        <option value="">-- Select Shop --</option>
                                                        <c:forEach items="${availableShops}" var="shop">
                                                            <option value="${shop.shopId}" ${param.shopId == shop.shopId ? 'selected' : ''}>
                                                                ${shop.name} (Source: ${shop.sourceCity})
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Fruit Selection -->
                                                <div class="col-md-6">
                                                    <label for="fruitId" class="form-label">Select Fruit <span class="text-danger">*</span></label>
                                                    <select class="form-select" id="fruitId" name="fruitId" required>
                                                        <option value="">-- Select Fruit --</option>
                                                        <c:forEach items="${availableFruits}" var="fruit">
                                                            <option value="${fruit.fruitId}" ${param.fruitId == fruit.fruitId ? 'selected' : ''}>
                                                                ${fruit.name} (Source: ${fruit.sourceCity})
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Quantity Input -->
                                                <div class="col-md-6">
                                                    <label for="quantity" class="form-label">Quantity <span class="text-danger">*</span></label>
                                                    <input type="number" class="form-control" id="quantity" 
                                                           name="quantity" min="1" max="1000" 
                                                           value="${param.quantity}" required>
                                                    <div class="form-text">Enter quantity between 1-1000</div>
                                                </div>

                                                <!-- Submit Button -->
                                                <div class="col-12">
                                                    <button type="submit" class="btn btn-primary">
                                                        <i class="bi bi-send me-2"></i>Submit Reservation
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${param.subtab == 'borrow-record'}">
                                <!-- Borrow Records Table -->
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
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td> 
                                                    <td>Approved</td> 
                                                </tr>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td> 
                                                    <td>Denied</td> 
                                                </tr>
                                                <tr>
                                                    <td>s00xs00xf00x</td>
                                                    <td>xxxx-xx-xx</td>
                                                    <td>xxxx shop 1</td>
                                                    <td>Apple</td>
                                                    <td>10</td> 
                                                    <td>Pending</td> 
                                                </tr>
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
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>f00x</td>
                                            <td>Apple</td>
                                            <td>20</td>
                                            <td><button type="submit" class="btn btn-primary">Update</button></td>
                                        </tr>
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
