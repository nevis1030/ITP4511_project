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
                    <!-- Add your borrow request form here -->
                </div>
            </div>
        </c:when>
        <c:when test="${param.subtab == 'borrow-record'}">
            <!-- Borrow Record Table -->
            <div class="card">
                <div class="card-body">
                    <!-- Add your borrow record table here -->
                </div>
            </div>
        </c:when>
    </c:choose>
</c:if>
