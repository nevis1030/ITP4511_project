<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Database Connection Test</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h1>Database Connection Test</h1>
            
            <div class="mt-4">
                <h3>Connection Status</h3>
                <div class="alert ${connectionStatus != null ? 'alert-success' : 'alert-danger'}">
                    ${connectionStatus != null ? connectionStatus : 'No connection status available'}
                </div>
            </div>

            <c:if test="${error != null}">
                <div class="alert alert-danger mt-4">
                    Error: ${error}
                </div>
            </c:if>

            <div class="mt-4">
                <h3>Stock Data</h3>
                <c:if test="${stockList != null && not empty stockList}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Stock ID</th>
                                <th>Fruit ID</th>
                                <th>Quantity</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="stock" items="${stockList}">
                                <tr>
                                    <td>${stock.stockId}</td>
                                    <td>${stock.fruitId}</td>
                                    <td>${stock.quantity}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${stockList == null || empty stockList}">
                    <div class="alert alert-warning">
                        No stock data available
                    </div>
                </c:if>
            </div>

            <div class="mt-4">
                <h3>Fruit Data</h3>
                <c:if test="${fruitList != null && not empty fruitList}">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Fruit ID</th>
                                <th>Name</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="fruit" items="${fruitList}">
                                <tr>
                                    <td>${fruit.fruitId}</td>
                                    <td>${fruit.name}</td>
                                    <td>${fruit.price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${fruitList == null || empty fruitList}">
                    <div class="alert alert-warning">
                        No fruit data available
                    </div>
                </c:if>
            </div>
        </div>
    </body>
</html>