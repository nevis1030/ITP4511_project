<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${param.tab == 'borrowing'}">
    <h3>Borrowing Fruit</h3>
    <ul class="nav nav-tabs mb-4">
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'borrow-request' ? 'active' : ''}" 
               href="?tab=borrowing&subtab=borrow-request">Borrow Request</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'borrow-record' ? 'active' : ''}" 
               href="?tab=borrowing&subtab=borrow-record">Borrow Record</a>
        </li>
    </ul>

    <c:choose>
        <c:when test="${param.subtab == 'borrow-request'}">
            <!-- Borrow Request Form -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">New Borrow Request</h5>
                    <form action="borrow" method="post">
                        <input type="hidden" name="action" value="create">
                        <input type="hidden" name="fromShopId" value="${sessionScope.shopId}">
                        
                        <div class="mb-3">
                            <label for="toShopId" class="form-label">To Shop</label>
                            <input type="text" class="form-control" id="toShopId" name="toShopId" required>
                        </div>
                        
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
                        
                        <button type="submit" class="btn btn-primary">Submit Request</button>
                    </form>
                </div>
            </div>
        </c:when>
        
        <c:when test="${param.subtab == 'borrow-record'}">
            <!-- Borrow Records Table -->
            <div class="card">
                <div class="card-body">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Borrow ID</th>
                                <th>From Shop</th>
                                <th>To Shop</th>
                                <th>Fruit</th>
                                <th>Quantity</th>
                                <th>Status</th>
                                <th>Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${borrows}" var="borrow">
                                <tr>
                                    <td>${borrow.borrowId}</td>
                                    <td>${borrow.fromShop}</td>
                                    <td>${borrow.toShop}</td>
                                    <td>
                                        <c:forEach items="${fruits}" var="fruit">
                                            <c:if test="${fruit.fruitId == borrow.fruitId}">
                                                ${fruit.fruitName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>${borrow.quantity}</td>
                                    <td>${borrow.status}</td>
                                    <td><fmt:formatDate value="${borrow.date}" pattern="yyyy-MM-dd"/></td>
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
