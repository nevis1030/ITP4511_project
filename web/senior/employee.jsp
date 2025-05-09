<%-- 
    Document   : employee
    Created on : 26 Apr 2025, 4:03:14 pm
    Author     : local_user
--%>
<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ict.bean.EmployeeBean"%>
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
                            <h5 class="card-title">Employee List</h5>
                            <a href="employee_add" class="btn btn-primary">New Employee</a>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Employee ID</th>
                                        <th>Role</th>
                                        <th>Region</th>
                                        <th>Location</th>
                                        <th>Name</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<EmployeeBean> users = (ArrayList<EmployeeBean>)request.getAttribute("users");
                                        if (users != null){
                                            for (EmployeeBean user : users){
                                                pageContext.setAttribute("user", user);
                                    %>  
                                    <tr>
                                        <td><%= user.getUserId()%></td>
                                        <td><%= user.getRole()%></td>
                                        <td><%= user.getRegion()%></td>
                                        <td><%= user.getLocation()%></td>
                                        <td><%= user.getName()%></td>
                                        <td>
                                            <form action="employee_edit" method="get">
                                                <input type="hidden" name="targetUser" value="<%= user.getUserId()%>">
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
