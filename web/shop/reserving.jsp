<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${param.tab == 'reserving'}">
    <h3>Reserving Fruit</h3>
    <ul class="nav nav-tabs mb-4">
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'request' ? 'active' : ''}" 
               href="?tab=reserving&subtab=request">Reserve Request</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'record' ? 'active' : ''}" 
               href="?tab=reserving&subtab=record">Reserve Record</a>
        </li>
    </ul>

    <c:choose>
        <c:when test="${param.subtab == 'request'}">
            <!-- Reserve Request Form -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">New Reservation</h5>
                    <form action="reserve" method="post">
                        <input type="hidden" name="action" value="create">
                        <input type="hidden" name="shopId" value="${sessionScope.shopId}">
                        
                        <div class="mb-3">
                            <label for="fruitId" class="form-label">Select Fruit</label>
                            <select class="form-select" id="fruitId" name="fruitId" required>
                                <c:forEach items="${fruits}" var="fruit">
                                    <option value="${fruit.fruitId}">${fruit.fruitName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="quantity" class="form-label">Quantity</label>
                            <input type="number" class="form-control" id="quantity" name="quantity" min="1" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Submit Reservation</button>
                    </form>
                </div>
            </div>
        </c:when>
        
        <c:when test="${param.subtab == 'record'}">
            <!-- Reservation Records Table -->
            <div class="card">
                <div class="card-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Reservation ID</th>
                                <th>Fruit</th>
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
                                    <td>${res.quantity}</td>
                                    <td><fmt:formatDate value="${res.orderDate}" pattern="yyyy-MM-dd"/></td>
                                    <td>${res.status}</td>
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