<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="mt-4">
    <h3>Stock Data</h3>
    <c:if test="${stocks != null && not empty stocks}">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Stock ID</th>
                    <th>Fruit ID</th>
                    <th>Fruit Name</th>
                    <th>Source City</th>
                    <th>Quantity</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="stock" items="${stocks}">
                    <tr>
                        <td>${stock.stockId}</td>
                        <td>${stock.fruitId}</td>
                        <td>
                            <c:forEach var="fruit" items="${fruits}">
                                <c:if test="${fruit.fruitId == stock.fruitId}">
                                    ${fruit.fruitName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="fruit" items="${fruits}">
                                <c:if test="${fruit.fruitId == stock.fruitId}">
                                    <c:forEach var="city" items="${cities}">
                                        <c:if test="${city.cityId == fruit.sourceCityId}">
                                            ${city.cityName}
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${stock.quantity}</td>
                        <td>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-sm btn-primary" 
                                        data-bs-toggle="modal" data-bs-target="#editStockModal${stock.stockId}">
                                    Edit
                                </button>
                                <form action="${pageContext.request.contextPath}/shop/stock/delete" method="post" 
                                      onsubmit="return confirm('Are you sure you want to delete this stock item?');">
                                    <input type="hidden" name="stockId" value="${stock.stockId}">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                            
                            <!-- Edit Modal for each stock item -->
                            <div class="modal fade" id="editStockModal${stock.stockId}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Edit Stock</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <form action="${pageContext.request.contextPath}/shop/stock/edit" method="post">
                                            <div class="modal-body">
                                                <input type="hidden" name="stockId" value="${stock.stockId}">
                                                <input type="hidden" name="fruitId" value="${stock.fruitId}">
                                                <input type="hidden" name="shopId" value="${stock.shopId}">
                                                
                                                <div class="mb-3">
                                                    <label class="form-label">Stock ID:</label>
                                                    <input type="text" class="form-control" value="${stock.stockId}" disabled>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Fruit:</label>
                                                    <input type="text" class="form-control" 
                                                           value="<c:forEach var="fruit" items="${fruits}"><c:if test="${fruit.fruitId == stock.fruitId}">${fruit.fruitName}</c:if></c:forEach>" 
                                                           disabled>
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Source City:</label>
                                                    <input type="text" class="form-control" 
                                                           value="<c:forEach var="fruit" items="${fruits}"><c:if test="${fruit.fruitId == stock.fruitId}"><c:forEach var="city" items="${cities}"><c:if test="${city.cityId == fruit.sourceCityId}">${city.cityName}</c:if></c:forEach></c:if></c:forEach>" 
                                                           disabled>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="quantity" class="form-label">Quantity:</label>
                                                    <input type="number" class="form-control" id="quantity" name="quantity" 
                                                           value="${stock.quantity}" required min="0">
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" class="btn btn-primary">Save changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${stocks == null || empty stocks}">
        <div class="alert alert-warning">
            No stock data available
        </div>
    </c:if>
</div>
