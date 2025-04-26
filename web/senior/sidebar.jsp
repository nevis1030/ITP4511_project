<%-- 
    Document   : sidebar
    Created on : 26 Apr 2025, 2:17:11 am
    Author     : local_user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="d-flex flex-column p-3" style="height: 100%">
    <h4 class="mb-3">
        <a href="senior_dashboard.jsp" class="text-decoration-none text-reset">AIB Dashboard</a>
    </h4>

    <!-- User Info -->
    <div class="mb-3">
        <p class="mb-1">Logged in as: ${sessionScope.user.username}</p>
        <p class="mb-1">User ID: ${sessionScope.user.userId}</p>
        <p class="mb-1">Role: 
            <select name="role" class="form-select" disabled="true">
                <option value="shopStaff" 
                        ${sessionScope.user.role == 0 ? 'selected' : ''}>
                    Shop Staff
                </option>
                <option value="warehouseStaff" 
                        ${sessionScope.user.role == 1 ? 'selected' : ''}>
                    Warehouse Staff
                </option>
                <option value="seniorManager" 
                        ${sessionScope.user.role == 2 ? 'selected' : ''}>
                    Senior Manager
                </option>
            </select>
        </p>
        

    </div>

    <nav class="nav flex-column">
        <!-- Shop Staff Menu -->

        <a class="nav-link" href="">Reservation</a>
        <a class="nav-link" href="">Consumption</a>
        <a class="nav-link" href="">Employee</a>
        <a class="nav-link" href="account">Account</a>
    </nav>

    <!-- Logout Button -->
    <a class="mt-auto btn btn-primary" href="logout">Logout</a>  
</div>
