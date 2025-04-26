<%-- 
    Document   : consumption
    Created on : 26 Apr 2025, 2:24:55 pm
    Author     : local_user
--%>

<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.ConsumptionBean"%>
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
                    <h3>Consumption Analytics</h3>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Consumption Record</h5>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Region</th>
                                        <th>Season</th>
                                        <th>Fruit</th>
                                        <th>Quantity</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<ConsumptionBean> list = (ArrayList<ConsumptionBean>)request.getAttribute("consumptions");
                                        if (list != null) {
                                            for(ConsumptionBean item: list){
                                                pageContext.setAttribute("item", item);
                                    %>
                                    <tr>
                                        <th><%= item.getConsumptionId()%></th>
                                        <th><%= item.getRegionId()%></th>
                                        <th><%= item.getSeason()%></th>
                                        <th><%= item.getFruitId()%></th>
                                        <th><%= item.getQuantity()%></th>
                                    </tr>
                                    <%
                                            }
                                        }
                                    %>
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
