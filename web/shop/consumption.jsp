<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="mt-4">
    <h3>Check out Records</h3>
    <c:if test="${consumptions != null && not empty consumptions}">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Consumption ID</th>
                    <th>Fruit ID</th>
                    <th>Region ID</th>
                    <th>Quantity</th>
                    <th>Season</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="consumption" items="${consumptions}">
                    <tr>
                        <td>${consumption.consumptionId}</td>
                        <td>${consumption.fruitId}</td>
                        <td>${consumption.regionId}</td>
                        <td>${consumption.quantity}</td>
                        <td>
                            <c:choose>
                                <c:when test="${consumption.season eq 0}">Spring</c:when>
                                <c:when test="${consumption.season eq 1}">Summer</c:when>
                                <c:when test="${consumption.season eq 2}">Fall</c:when>
                                <c:when test="${consumption.season eq 3}">Winter</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-sm btn-primary" 
                                        data-bs-toggle="modal" data-bs-target="#editConsumptionModal${consumption.consumptionId}">
                                    Edit
                                </button>
                                <form action="${pageContext.request.contextPath}/shop/consumption" method="post" 
                                      onsubmit="return confirm('Are you sure you want to delete this consumption record?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="consumptionId" value="${consumption.consumptionId}">
                                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                                </form>
                            </div>
                            
                            <!-- Edit Modal for each consumption record -->
                            <div class="modal fade" id="editConsumptionModal${consumption.consumptionId}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title">Edit Consumption Record</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <form action="${pageContext.request.contextPath}/shop/consumption" method="post">
                                            <input type="hidden" name="action" value="edit">
                                            <div class="modal-body">
                                                <input type="hidden" name="consumptionId" value="${consumption.consumptionId}">
                                                
                                                <div class="mb-3">
                                                    <label class="form-label">Consumption ID:</label>
                                                    <input type="text" class="form-control" value="${consumption.consumptionId}" disabled>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="fruitId" class="form-label">Fruit ID:</label>
                                                    <select class="form-select" id="fruitId" name="fruitId" required>
                                                        <c:forEach var="fruit" items="${availableFruits}">
                                                            <option value="${fruit.fruitId}" ${consumption.fruitId eq fruit.fruitId ? 'selected' : ''}>
                                                                ${fruit.fruitId} - ${fruit.fruitName}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="regionId" class="form-label">Region ID:</label>
                                                    <select class="form-select" id="regionId" name="regionId" required>
                                                        <c:forEach var="region" items="${availableRegions}">
                                                            <option value="${region}" ${consumption.regionId eq region ? 'selected' : ''}>
                                                                ${region}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="mb-3">
                                                    <label for="quantity" class="form-label">Quantity:</label>
                                                    <input type="number" class="form-control" id="quantity" name="quantity" 
                                                           value="${consumption.quantity}" required min="0">
                                                </div>
                                                <div class="mb-3">
                                                    <label for="season" class="form-label">Season:</label>
                                                    <select class="form-select" id="season" name="season" required>
                                                        <option value="0" ${consumption.season eq 0 ? 'selected' : ''}>Spring</option>
                                                        <option value="1" ${consumption.season eq 1 ? 'selected' : ''}>Summer</option>
                                                        <option value="2" ${consumption.season eq 2 ? 'selected' : ''}>Fall</option>
                                                        <option value="3" ${consumption.season eq 3 ? 'selected' : ''}>Winter</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <input type="hidden" name="consumptionId" value="${consumption.consumptionId}">
                                                <input type="hidden" name="fruitId" value="${consumption.fruitId}">
                                                <input type="hidden" name="regionId" value="${consumption.regionId}">
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
    <c:if test="${consumptions == null || empty consumptions}">
        <div class="alert alert-warning">
            No consumption records available
        </div>
    </c:if>
</div>
