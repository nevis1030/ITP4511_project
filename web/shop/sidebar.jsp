<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-md-3 col-lg-2 sidebar">
    <div class="d-flex flex-column p-3" style="height: 100%">
        <h4 class="mb-3">
            <a href="${pageContext.request.contextPath}/shop_dashboard.jsp" class="text-decoration-none text-reset">AIB Dashboard</a>
        </h4>
        
        <!-- User Info -->
        <div class="mb-3">
            <p class="mb-1">Logged in as: ${sessionScope.user.username}</p>
            <p class="mb-0">Role: ${sessionScope.user.role}</p>
        </div>

        <nav class="nav flex-column">
            <!-- Shop Staff Menu -->
            <c:if test="${sessionScope.shopId != null}">
                <a class="nav-link ${param.tab == 'reserving' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/shop/reserve">Reserving Fruit</a>
                <a class="nav-link ${param.tab == 'borrowing' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/shop/borrow">Borrowing Fruit</a>
                <a class="nav-link ${param.tab == 'stock' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/shop/stock">Stock Level</a>
                <a class="nav-link ${param.tab == 'add-fruit' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/shop/add-fruit">Add Fruit</a>
                <a class="nav-link ${param.tab == 'account' ? 'active' : ''}" 
                   href="${pageContext.request.contextPath}/shop_dashboard.jsp?tab=account">Account</a>
            </c:if>
        </nav>

        <!-- Logout Button -->
        <a class="mt-auto btn btn-primary" href="${pageContext.request.contextPath}/logout">Logout</a>  
    </div>
</div>
