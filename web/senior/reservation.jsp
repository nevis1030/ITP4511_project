<%-- 
    Document   : reservation
    Created on : 26 Apr 2025, 2:24:30 pm
    Author     : local_user
--%>

<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.ReserveRecordBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <h3>Reservation Analytics</h3>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Reservation Record</h5>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Shop ID</th>
                                        <th>Shop Name</th>
                                        <th>Fruit ID</th>
                                        <th>Fruit Name</th>
                                        <th>Quantity</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<ReserveRecordBean> list = (ArrayList<ReserveRecordBean>)request.getAttribute("list");
                                        if(list != null) {
                                            for(ReserveRecordBean item : list){
                                                pageContext.setAttribute("item", item);
                                    %>
                                    <tr>
                                        <td><%= item.getReservation_id()%></td>
                                        <td><%= item.getShop_id()%></td>
                                        <td><%= item.getShop_name()%></td>
                                        <td><%= item.getFruit_id()%></td>
                                        <td><%= item.getFruit_name()%></td>
                                        <td><%= item.getQuantity()%></td>
                                    </tr>
                                    <%
                                            }
                                        }
                                    %>
                                    <!-- Add dynamic data here -->
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

