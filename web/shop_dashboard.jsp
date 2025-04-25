<%-- 
    Document   : shop_dashboard
    Created on : 23 Apr 2025, 1:42:51 pm
    Author     : local_user
--%>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.FruitBean" %>
<%@page import="ict.bean.StockLevelBean" %>
<%@page import="ict.bean.ReservationBean" %>
<%@page import="ict.bean.BorrowBean" %>
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
        <c:if test="${sessionScope.user == null}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <div class="col-md-3 col-lg-2 sidebar">
                    <div class="d-flex flex-column p-3" style="height: 100%">
                        <h4 class="mb-3">
                            <a href="?" class="text-decoration-none text-reset">AIB Dashboard</a>
                        </h4>
                        
                        <!-- User Info -->
                        <div class="mb-3">
                            <p class="mb-1">Logged in as: ${sessionScope.user.username}</p>
                            <p class="mb-0">Role: ${sessionScope.user.role}</p>
                        </div>

                        <nav class="nav flex-column">
                            <!-- Shop Staff Menu -->
                            <c:if test="${sessionScope.shopId != null}">
                                <a class="nav-link ${param.tab == 'reserving' ? 'active' : ''}" 
                                   href="?tab=reserving">Reserving Fruit</a>
                                <a class="nav-link ${param.tab == 'borrowing' ? 'active' : ''}" 
                                   href="?tab=borrowing">Borrowing Fruit</a>
                                <a class="nav-link ${param.tab == 'stock' ? 'active' : ''}" 
                                   href="display-stock">Stock Level</a>
                                <a class="nav-link ${param.tab == 'account' ? 'active' : ''}" 
                                   href="?tab=account">Account</a>
                            </c:if>
                        </nav>

                        <!-- Logout Button -->
                        <a class="mt-auto btn btn-primary" href="logout">Logout</a>  
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
                                        <form action="${pageContext.request.contextPath}/reserve" method="post">
                                            <input type="hidden" name="action" value="create">
                                            
                                            <div class="mb-3">
                                                <label for="fruitId" class="form-label">Select Fruit</label>
                                                <select class="form-select" id="fruitId" name="fruitId" required>
                                                    <c:forEach items="${fruits}" var="fruit">
                                                        <option value="${fruit.fruitId}">${fruit.fruitName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="quantity" class="form-label">Quantity</label>
                                                <input type="number" class="form-control" id="quantity" name="quantity" min="1" required>
                                            </div>
                                            
                                            <button type="submit" class="btn btn-primary">Submit Reservation</button>
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
                                                    <th>Reservation ID</th>
                                                    <th>Fruit</th>
                                                    <th>Quantity</th>
                                                    <th>Order Date</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${reservations}" var="res">
                                                    <tr>
                                                        <td>${res.reservationId}</td>
                                                        <td>${res.fruitId}</td>
                                                        <td>${res.quantity}</td>
                                                        <td><fmt:formatDate value="${res.orderDate}" pattern="yyyy-MM-dd"/></td>
                                                        <td>${res.status}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:if>

                    <!-- Borrowing Fruit Content -->
                    <c:if test="${param.tab == 'borrowing'}">
                        <h3>Borrowing Fruit</h3>
                        <ul class="nav nav-tabs mb-4">
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
                            <c:when test="${param.subtab == 'borrow-request'}">
                                <!-- Borrow Request Form -->
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">New Borrow Request</h5>
                                        <form action="${pageContext.request.contextPath}/borrow" method="post">
                                            <input type="hidden" name="action" value="create">
                                            
                                            <div class="mb-3">
                                                <label for="toShopId" class="form-label">To Shop</label>
                                                <input type="text" class="form-control" id="toShopId" name="toShopId" required>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="fruitId" class="form-label">Select Fruit</label>
                                                <select class="form-select" id="fruitId" name="fruitId" required>
                                                    <c:forEach items="${fruits}" var="fruit">
                                                        <option value="${fruit.fruitId}">${fruit.fruitName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            
                                            <div class="mb-3">
                                                <label for="quantity" class="form-label">Quantity</label>
                                                <input type="number" class="form-control" id="quantity" name="quantity" min="1" required>
                                            </div>
                                            
                                            <button type="submit" class="btn btn-primary">Submit Request</button>
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
                                                    <th>Borrow ID</th>
                                                    <th>From Shop</th>
                                                    <th>To Shop</th>
                                                    <th>Fruit</th>
                                                    <th>Quantity</th>
                                                    <th>Status</th>
                                                    <th>Date</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${borrows}" var="borrow">
                                                    <tr>
                                                        <td>${borrow.borrowId}</td>
                                                        <td>${borrow.fromShop}</td>
                                                        <td>${borrow.toShop}</td>
                                                        <td>${borrow.fruitId}</td>
                                                        <td>${borrow.quantity}</td>
                                                        <td>${borrow.status}</td>
                                                        <td><fmt:formatDate value="${borrow.date}" pattern="yyyy-MM-dd"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:if>

                    <!-- Stock Level Content -->
                    <c:if test="${param.tab == 'stock'}">
                        <div class="mt-4">
                            <h3>Stock Data</h3>
                            <c:if test="${stocks != null && not empty stocks}">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Stock ID</th>
                                            <th>Fruit ID</th>
                                            <th>Fruit Name</th>
                                            <th>Quantity</th>
                                            <th>Warehouse ID</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="stock" items="${stocks}">
                                            <tr>
                                                <td>${stock.stockId}</td>
                                                <td>${stock.fruitId}</td>
                                                <td>
                                                    <c:forEach var="fruit" items="${fruits}">
                                                        <c:if test="${fruit.fruitId == stock.fruitId}">
                                                            ${fruit.fruitName}
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>${stock.quantity}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <c:if test="${stocks == null || empty stocks}">
                                <div class="alert alert-warning">
                                    No stock data available
                                </div>
                            </c:if>
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
