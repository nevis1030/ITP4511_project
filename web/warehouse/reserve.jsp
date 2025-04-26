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
<%@page import="ict.bean.WarehouseReserveBean"%>
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
                            <h5>Reservation Record</h5>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Shop ID</th>
                                        <th>Shop Name</th>
                                        <th>Fruit ID</th>
                                        <th>Fruit Name</th>
                                        <th>Quantity</th>
                                        <th>Order Date</th>
                                        <th>End Date</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<WarehouseReserveBean> list = (ArrayList<WarehouseReserveBean>)request.getAttribute("list");
                                        if (list != null){
                                            for(WarehouseReserveBean item : list){
                                                pageContext.setAttribute("item", item);
                                    %>
                                    <tr>
                                        <td><%= item.getReservationId()%></td>
                                        <td><%= item.getShopId()%></td>
                                        <td><%= item.getShopName()%></td>
                                        <td><%= item.getFruitId()%></td>
                                        <td><%= item.getFruitName()%></td>
                                        <td><%= item.getQuantity()%></td>
                                        <td><%= item.getOrderDate()%></td>
                                        <td><%= item.getEndDate()%></td>
                                        <td><%= item.getStatus()%></td>
                                        <td>
                                            
                                            <%
                                                if (item.getStatus().equals("Approved")||item.getStatus().equals("Denied")||item.getStatus().equals("Completed")){
                                            %>                                          
                                            <form action="" method="post">
                                                <input type="hidden" name="reservationId" value="<%= item.getReservationId()%>">
                                                <input type="submit" class="btn btn-primary" value="Approve" disabled="true">
                                            </form>
                                            <form action="" method="post">
                                                <input type="hidden" name="reservationId" value="<%= item.getReservationId()%>">
                                                <input type="submit" class="btn btn-danger" value="Deny" disabled="true">
                                            </form>
                                            <%    
                                                }else{
                                            %>
                                            <form action="warehouse_reservation_edit" method="post">
                                                <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                                                <input type="hidden" name="reservationId" value="<%= item.getReservationId()%>">
                                                <input type="hidden" name="approval" value="1">
                                                <input type="submit" class="btn btn-primary" value="Approve">
                                            </form>
                                            <form action="warehouse_reservation_edit" method="post">
                                                <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                                                <input type="hidden" name="reservationId" value="<%= item.getReservationId()%>">
                                                <input type="hidden" name="approval" value="2">
                                                <input type="submit" class="btn btn-danger" value="Deny">
                                            </form>
                                            <%        
                                                }
                                            %>
                                            
                                        </td>
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
