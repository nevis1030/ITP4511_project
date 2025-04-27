<%@page import="java.time.LocalDate" %>
<%@page import="java.time.ZoneId" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.FruitBean"%>
<%@page import="ict.bean.CityBean"%>
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
                <%
                    FruitBean fruit = (FruitBean)request.getAttribute("fruit");
                    if(fruit != null){
                        pageContext.setAttribute("fruit", fruit);
                    }
                %>
                <!-- Main Content -->
                <div class="col-md-9 col-lg-10 p-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Fruit Edit</h5>
                            <form action="senior_fruit_edit" method="post">
                                <input type="hidden" name="fruitId" value="<%= fruit.getFruitId()%>">
                                Fruit ID: <input type="text" value="<%= fruit.getFruitId()%>" disabled="true"><br><br>
                                Fruit Name: <input type="text" name="fruitName" value="<%= fruit.getFruitName()%>"><br><br>
                                Source City:
                                <select name="sourceCityId" class="form-select">
                                    <%
                                        ArrayList<CityBean> cities = (ArrayList<CityBean>)request.getAttribute("cities");
                                        if(cities != null){
                                            for(CityBean city : cities){
                                                if(city.getCityId().equals(fruit.getSourceCityId())){
                                    %>
                                    <option value="<%= city.getCityId()%>" selected>
                                        City: <%=city.getCityName()%>, ID: <%= city.getCityId()%>
                                    </option>
                                    <%                
                                                    continue;
                                                }else{
                                    %>
                                    <option value="<%= city.getCityId()%>">
                                        City: <%=city.getCityName()%>, ID: <%= city.getCityId()%>
                                    </option>
                                    <%            
                                                }
                                            }
                                        }
                                    %>
                                </select><br><br>
                                <input type="submit" value="Change">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>