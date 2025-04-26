<%-- 
    Document   : stocklevel
    Created on : 26 Apr 2025, 10:53:18 pm
    Author     : local_user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.WarehouseStockEditBean"%>
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
                    <div class="card">
                        <div class="card-body">
                            <h5>Stock Edit</h5>
                            <%
                                WarehouseStockEditBean item = (WarehouseStockEditBean)request.getAttribute("item");
                                if (item != null){
                                    pageContext.setAttribute("item", item);
                                }
                            %>
                            <form action="warehouse_stock_edit" method="post">
                                <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                                <input type="hidden" name="fruitId" value="<%= item.getFruit_id()%>">
                                Fruit ID: <input type="text" value="<%= item.getFruit_id()%>" disabled="true"><br><br>
                                Fruit Name: <input type="text" value="<%= item.getFruit_name()%>" disabled="true"><br><br>
                                Quantity: <input type="number" name="quantity" value="<%= item.getQuantity()%>">
                                <input type="submit" class="btn btn-primary" value="Change">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
