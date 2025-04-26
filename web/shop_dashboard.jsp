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
        <%@include file="shop/title.jsp"%>
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
                <%@include file="shop/sidebar.jsp"%>

                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 p-4">
                    <!-- Welcome Content -->
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

                    <!-- Include specific content based on tab -->
                    <c:if test="${param.tab == 'reserving'}">
                        <%@include file="shop/reserving.jsp"%>
                    </c:if>
                    <c:if test="${param.tab == 'borrowing'}">
                        <%@include file="shop/borrowing.jsp"%>
                    </c:if>
                    <c:if test="${param.tab == 'stock'}">
                        <%@include file="shop/stock.jsp"%>
                    </c:if>
                    <c:if test="${param.tab == 'account'}">
                        <%@include file="shop/account.jsp"%>
                    </c:if>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
