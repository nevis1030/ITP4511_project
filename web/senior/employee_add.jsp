<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.UserBean"%>
<%@page import="ict.bean.ShopBean"%>
<%@page import="ict.bean.WarehouseBean"%>
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
                            <h5 class="card-title">Employee Add</h5>
                            <form action="employee_add" method="post">
                                <%
                                    String userId = (String)request.getAttribute("userId");
                                    if(userId != null){
                                        pageContext.setAttribute("userId", userId);
                                    }
                                %>
                                <input type="hidden" name="userId" value="<%= userId%>">
                                Name: <input type="text" name="displayName"><br><br>
                                User ID: <input type="text" value="<%= userId%>" disabled="true"><br><br>
                                Account Name: <input type="text" name="username"><br><br>
                                Password: <input type="text" name="password"><br><br>
                                Role: 
                                <select name="role" class="form-select">
                                    <option value="0">Shop Staff</option>
                                    <option value="1">Warehouse Staff</option>
                                    <option value="2">Senior Manager</option>
                                </select><br><br>
                                Shop: 
                                <select name="shopId" class="form-select">
                                    <option value="null">NOT SHOP STAFF</option>
                                    <%
                                        ArrayList<ShopBean> shops = (ArrayList<ShopBean>)request.getAttribute("shops");
                                        if(shops != null){
                                            for(ShopBean shop : shops){
                                                pageContext.setAttribute("shop", shop);
                                    %>
                                    <option value="<%= shop.getShopId()%>">Shop: <%=shop.getShopName()%>, ID: <%=shop.getShopId()%></option>
                                    <%            
                                            }
                                        }
                                    %>
                                </select><br><br>
                                Warehouse: 
                                <select name="warehouseId" class="form-select">
                                    <option value="null">NOT SHOP STAFF</option>
                                    <%
                                        ArrayList<WarehouseBean> warehouses = (ArrayList<WarehouseBean>)request.getAttribute("warehouses");
                                        if(shops != null){
                                            for(WarehouseBean warehouse : warehouses){
                                                pageContext.setAttribute("warehouse", warehouse);
                                    %>
                                    <option value="<%= warehouse.getWarehouseId()%>">Warehouse: <%=warehouse.getWarehouseName()%>, ID: <%=warehouse.getWarehouseId()%></option>
                                    <%            
                                            }
                                        }
                                    %>
                                </select><br><br>
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