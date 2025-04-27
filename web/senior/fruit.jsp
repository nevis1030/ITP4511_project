<%-- 
    Document   : shop_dashboard
    Created on : 23 Apr 2025, 1:42:51 pm
    Author     : local_user
--%>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.SeniorFruitBean"%>
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
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Fruit Management</h5>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Fruit ID</th>
                                        <th>Fruit Name</th>
                                        <th>Source City ID</th>
                                        <th>Source City</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<SeniorFruitBean> list = (ArrayList<SeniorFruitBean>)request.getAttribute("list");
                                        if(list != null){
                                            for(SeniorFruitBean item : list){
                                    %>
                                    <tr>
                                        <td><%=item.getFruitId()%></td>
                                        <td><%=item.getFruitName()%></td>
                                        <td><%=item.getSourceCityId()%></td>
                                        <td><%=item.getSourceCityName()%></td>
                                        <td>
                                            <form action="senior_fruit_info" method="post">
                                                <input type="hidden" name="fruitId" value="<%=item.getFruitId()%>">
                                                <input type="hidden" name="fruitName" value="<%=item.getFruitName()%>">
                                                <input type="hidden" name="cityId" value="<%=item.getSourceCityId()%>">
                                                <input type="submit" class="btn btn-primary" value="Edit">
                                            </form>
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
