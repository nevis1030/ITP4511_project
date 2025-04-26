<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${param.tab == 'account'}">
    <h3>Account Settings</h3>
    <ul class="nav nav-tabs mb-4">
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'password' ? 'active' : ''}" 
               href="?tab=account&subtab=password">Change Password</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${param.subtab == 'details' ? 'active' : ''}" 
               href="?tab=account&subtab=details">Change/View Details</a>
        </li>
    </ul>

    <c:choose>
        <c:when test="${param.subtab == 'password'}">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Change Password</h5>
                    <form action="shop_password_change" method="post">
                        <p>Please contact you supervisor to change password</p>
                    </form>
                </div>
            </div>
        </c:when>

        <c:when test="${param.subtab == 'details'}">
            <div class="card">
                <div class="card-body">
                    <h5>Account Detail</h5>
                    <form>
                        User ID: <input type="text" name="userId" value="${sessionScope.user.userId}" disabled="true"><br><br>
                        Name: <input type="text" name="displayName" value="${sessionScope.user.displayName}" disabled="true"><br><br>
                        Role: 
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
                    </form>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <p>Select a tab to begin</p>
        </c:otherwise>
    </c:choose>
</c:if>