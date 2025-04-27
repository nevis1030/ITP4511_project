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
<%@page import="ict.bean.FruitBean"%>
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
                            <h5>New Stock</h5>
                            <form action="warehouse_stock_add" method="post">
                                <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                                <input type="hidden" name="warehouseId" value="${sessionScope.user.warehouseId}">
                                Warehouse ID: <input type="text" value="${sessionScope.user.warehouseId}" disabled="true"><br><br>
                                Quantity: <input type="number" name="quantity" min="0" value="1"><br><br>
                                Fruit:
                                <select name="fruitId">
                                    <%
                                        ArrayList<FruitBean> list = (ArrayList<FruitBean>)request.getAttribute("list");
                                        if(list != null){
                                            for(FruitBean item : list){
                                                pageContext.setAttribute("item", item);
                                    %>
                                    <option value="<%=item.getFruitId()%>">Fruit Name: <%=item.getFruitName()%>, ID: <%=item.getFruitId()%></option>
                                    <%            
                                            }
                                        }
                                    %>
                                </select>
                                <input type="submit" class="btn btn-primary" value="Add">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
