<%-- 
    Document   : title
    Created on : 23 Apr 2025, 1:30:46 pm
    Author     : local_user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<title>
    <c:choose>
        <c:when test="${not empty pageTitle}">
            AIB Bakery - ${pageTitle}
        </c:when>
        <c:otherwise>
            AIB Bakery
        </c:otherwise>
    </c:choose>
</title>
