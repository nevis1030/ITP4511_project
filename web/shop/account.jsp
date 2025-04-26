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
                    <form action="account" method="post">
                        <input type="hidden" name="action" value="change-password">
                        <input type="hidden" name="userId" value="${sessionScope.user.userId}">
                        
                        <div class="mb-3">
                            <label for="currentPassword" class="form-label">Current Password</label>
                            <input type="password" class="form-control" id="currentPassword" 
                                   name="currentPassword" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password</label>
                            <input type="password" class="form-control" id="newPassword" 
                                   name="newPassword" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirm New Password</label>
                            <input type="password" class="form-control" id="confirmPassword" 
                                   name="confirmPassword" required>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Change Password</button>
                    </form>
                </div>
            </div>
        </c:when>
        
        <c:when test="${param.subtab == 'details'}">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Account Details</h5>
                    <form action="account" method="post">
                        <input type="hidden" name="action" value="update">
                        
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" 
                                   name="username" value="${sessionScope.user.username}" readonly>
                        </div>
                
                        
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                    </form>
                </div>
            </div>
        </c:when>
        
        <c:otherwise>
            <p>Select a tab to begin</p>
        </c:otherwise>
    </c:choose>
</c:if>