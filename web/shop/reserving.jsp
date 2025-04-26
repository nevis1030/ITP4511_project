<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${param.tab == 'reserving'}">
    <h3>Reserving Fruit</h3>
    <ul class="nav nav-tabs mb-4">
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'request' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/shop/reserve?subtab=request">Reserve Request</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'record' ? 'active' : ''}" 
               href="${pageContext.request.contextPath}/shop/reserve?subtab=record">Reserve Record</a>
        </li>
    </ul>

    <c:choose>
        <c:when test="${param.subtab == 'request'}">
            <!-- Reserve Request Form -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">New Reservation</h5>
                    
                    <!-- Display error message if any -->
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger" role="alert">
                            ${param.error}
                        </div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/shop/reserve" method="post">
                        <input type="hidden" name="action" value="create">
                        <input type="hidden" name="shopId" value="${sessionScope.shopId}">
                        
                        <div class="mb-3">
                            <label for="fruitId" class="form-label">Select Fruit</label>
                            <select class="form-select" id="fruitId" name="fruitId" required>
                                <option value="">-- Select Fruit --</option>
                                <c:forEach items="${fruits}" var="fruit">
                                    <option value="${fruit.fruitId}">${fruit.fruitName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="orderDate" class="form-label">Order Date</label>
                            <input type="date" class="form-control" id="orderDate" name="orderDate" required>
                            <small class="form-text text-muted">Select a date within the next 14 days</small>
                        </div>
                        
                        <div class="mb-3">
                            <label for="quantity" class="form-label">Quantity</label>
                            <input type="number" class="form-control" id="quantity" name="quantity" min="1" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Submit Reservation</button>
                    </form>
                    
                    <!-- Add date restriction script -->
                    <script>
                        // Set min date to tomorrow and max date to 14 days from now
                        window.onload = function() {
                            var today = new Date();
                            var tomorrow = new Date(today);
                            tomorrow.setDate(today.getDate() + 1);
                            
                            var maxDate = new Date(today);
                            maxDate.setDate(today.getDate() + 14);
                            
                            // Format dates as YYYY-MM-DD for the date input
                            var tomorrowFormatted = tomorrow.toISOString().split('T')[0];
                            var maxDateFormatted = maxDate.toISOString().split('T')[0];
                            
                            var dateInput = document.getElementById('orderDate');
                            dateInput.min = tomorrowFormatted;
                            dateInput.max = maxDateFormatted;
                            dateInput.value = tomorrowFormatted;
                        };
                    </script>
                </div>
            </div>
        </c:when>
        
        <c:when test="${param.subtab == 'record'}">
            <!-- Reservation Records Table -->
            <div class="card">
                <div class="card-body">
                    <!-- Display success/error messages if any -->
                    <c:if test="${not empty param.success}">
                        <div class="alert alert-success" role="alert">
                            ${param.success}
                        </div>
                    </c:if>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger" role="alert">
                            ${param.error}
                        </div>
                    </c:if>
                    
                    <!-- Debug information -->
                    <c:if test="${empty reservations}">
                        <div class="alert alert-warning">
                            No reservation records found.
                        </div>
                    </c:if>
                    
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Reservation ID</th>
                                <th>Fruit</th>
                                <th>Source City</th>
                                <th>Quantity</th>
                                <th>Order Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${reservations}" var="res">
                                <tr>
                                    <td>${res.reservationId}</td>
                                    <td>
                                        <c:forEach items="${fruits}" var="fruit">
                                            <c:if test="${fruit.fruitId == res.fruitId}">
                                                ${fruit.fruitName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach items="${fruits}" var="fruit">
                                            <c:if test="${fruit.fruitId == res.fruitId}">
                                                <c:forEach items="${cities}" var="city">
                                                    <c:if test="${city.cityId == fruit.sourceCityId}">
                                                        ${city.cityName}
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>${res.quantity}</td>
                                    <td>${res.orderDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${res.status == 0}">
                                                <span class="badge bg-warning text-dark">Pending</span>
                                            </c:when>
                                            <c:when test="${res.status == 1}">
                                                <span class="badge bg-success">Approved</span>
                                            </c:when>
                                            <c:when test="${res.status == 2}">
                                                <span class="badge bg-danger">Denied</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Unknown</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:when>
        
        <c:otherwise>
            <p>Select a tab to begin</p>
        </c:otherwise>
    </c:choose>
</c:if>