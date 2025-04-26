<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.UserBean"%>
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
                    <h3>Employee</h3>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Employee Edit</h5>
                            <%
                              UserBean user = (UserBean)request.getAttribute("user");
                              ArrayList<String> shopList = (ArrayList<String>)request.getAttribute("shopList");
                              ArrayList<String> warehouseList = (ArrayList<String>)request.getAttribute("warehouseList");
                              if (user != null){
                                    pageContext.setAttribute("user", user);
                            %>
                            <form action="employee_edit" method="post">
                                <input type="hidden" name="userId" value="<%= user.getUserId()%>">
                                Name: <input type="text" name="displayName" value="<%= user.getDisplayName()%>"><br><br>
                                User ID: <input type="text" name="" value="<%= user.getUserId()%>" disabled="true"><br><br>
                                Account Name: <input type="text" name="username" value="<%= user.getUsername()%>"><br><br>
                                Password: <input type="text" name="password" value="<%= user.getPassword()%>"><br><br>
                                Role: 
                                <select name="role" class="form-select">
                                    <option value="0" 
                                            ${user.role == 0 ? 'selected' : ''}>
                                        Shop Staff
                                    </option>
                                    <option value="1" 
                                            ${user.role == 1 ? 'selected' : ''}>
                                        Warehouse Staff
                                    </option>
                                    <option value="2" 
                                            ${user.role == 2 ? 'selected' : ''}>
                                        Senior Manager
                                    </option>
                                </select><br><br>
                                Shop: 
                                <select name="shopId" class="form-select">
                                    <%
                                        if (user.getRole()==2){
                                    %>
                                    <option value="null">Management</option>
                                    <%    
                                        }  
                                      for(String shopId: shopList){
                                        if (user.getShopId() != null){
                                            if(user.getShopId().equals(shopId)){
                                    %>
                                    <option value="<%= shopId%>" selected><%= shopId%></option>
                                    <%
                                                continue;
                                            }
                                        }
                                    %>
                                    <option value="<%= shopId%>"><%= shopId%></option>
                                    <%
                                        }
                                        if (user.getShopId() == null){
                                    %>
                                    <option value="null" selected>Not shop staff</option>
                                    <%
                                        }else{
                                    %>
                                    <option value="null">Not shop staff</option>
                                    <%
                                        }
                                    %>
                                </select><br><br>
                                Warehouse: 
                                <select name="warehouseId" class="form-select">
                                    <%
                                      if (user.getRole()==2){
                                    %>
                                    <option value="null">Management</option>
                                    <%    
                                        }  
                                      for(String warehouseId: warehouseList){
                                        if (user.getWarehouseId() != null){
                                            if(user.getWarehouseId().equals(warehouseId)){
                                    %>
                                    <option value="<%= warehouseId%>" selected><%= warehouseId%></option>
                                    <%
                                                continue;
                                            }
                                        }
                                    %>
                                    <option value="<%= warehouseId%>"><%= warehouseId%></option>
                                    <%
                                        }  
                                        if(user.getWarehouseId() == null){
                                    %>
                                    <option value="null" selected>Not warehouse staff</option>
                                    <%
                                        }else{
                                    %>
                                    <option value="null">Not warehouse staff</option>
                                    <%            
                                        }
                                    %>
                                </select><br><br>
                                <input type="submit" class="btn btn-primary" value="Change">
                            </form>
                            <%
                              }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>