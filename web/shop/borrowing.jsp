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
                    
                    <!-- Display error message if any -->
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger" role="alert">
                            ${param.error}
                        </div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/shop/borrow" method="post">
                        <input type="hidden" name="action" value="create">
                        <input type="hidden" name="toShopId" value="${sessionScope.shopId}">
                        
                        <div class="mb-3">
                            <label for="fromShopId" class="form-label">Lending Shop</label>
                            <select class="form-select" id="fromShopId" name="fromShopId" required>
                                <option value="">-- Select Shop --</option>
                                <c:forEach items="${shops}" var="shop">
                                    <option value="${shop.shopId}">${shop.shopName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
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
                    <c:if test="${empty borrows}">
                        <div class="alert alert-warning">
                            No borrow records found.
                        </div>
                    </c:if>
                    
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Borrow ID</th>
                                <th>Requesting Shop</th>
                                <th>Lending Shop</th>
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
                                    <td>${shopNames[borrow.toShop]}</td>
                                    <td>${shopNames[borrow.fromShop]}</td>
                                    <td>
                                        <c:forEach items="${fruits}" var="fruit">
                                            <c:if test="${fruit.fruitId == borrow.fruitId}">
                                                ${fruit.fruitName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>${borrow.quantity}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${borrow.status == 0}">
                                                <span class="badge bg-warning text-dark">Pending</span>
                                            </c:when>
                                            <c:when test="${borrow.status == 1}">
                                                <span class="badge bg-success">Approved</span>
                                            </c:when>
                                            <c:when test="${borrow.status == 2}">
                                                <span class="badge bg-danger">Denied</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">Unknown</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${borrow.date}</td>
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
