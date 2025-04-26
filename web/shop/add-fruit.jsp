<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${param.tab == 'add-fruit'}">
    <h3>Add New Fruit</h3>
    
    <div class="row">
        <div class="col-md-6">
            <!-- Add Fruit Form -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Add Fruit</h5>
                    
                    <!-- Display error message if any -->
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger" role="alert">
                            ${param.error}
                        </div>
                    </c:if>
                    
                    <!-- Display success message if any -->
                    <c:if test="${not empty param.success}">
                        <div class="alert alert-success" role="alert">
                            ${param.success}
                        </div>
                    </c:if>
                    
                    <form action="${pageContext.request.contextPath}/shop/add-fruit" method="post">                        
                        <div class="mb-3">
                            <label for="fruitName" class="form-label">Fruit Name</label>
                            <input type="text" class="form-control" id="fruitName" name="fruitName" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="cityId" class="form-label">Source City</label>
                            <select class="form-select" id="cityId" name="cityId" required>
                                <option value="">-- Select Source City --</option>
                                <c:forEach items="${cities}" var="city">
                                    <option value="${city.cityId}">${city.cityName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Add Fruit</button>
                    </form>
                </div>
            </div>
        </div>
        
        <div class="col-md-6">
            <!-- Existing Fruits List -->
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Existing Fruits</h5>
                    
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Fruit ID</th>
                                <th>Fruit Name</th>
                                <th>Source City</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="fruit" items="${fruits}">
                                <tr>
                                    <td>${fruit.fruitId}</td>
                                    <td>${fruit.fruitName}</td>
                                    <td>
                                        <c:forEach var="city" items="${cities}">
                                            <c:if test="${city.cityId == fruit.sourceCityId}">
                                                ${city.cityName}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <button type="button" class="btn btn-sm btn-primary" 
                                                    data-bs-toggle="modal" 
                                                    data-bs-target="#editFruitModal${fruit.fruitId}">
                                                Edit
                                            </button>
                                            <form action="${pageContext.request.contextPath}/shop/delete-fruit" method="post" style="display:inline;">
                                                <input type="hidden" name="fruitId" value="${fruit.fruitId}">
                                                <button type="submit" class="btn btn-sm btn-danger" 
                                                        onclick="return confirm('Are you sure you want to delete this fruit?')">
                                                    Delete
                                                </button>
                                            </form>
                                        </div>
                                        
                                        <!-- Edit Fruit Modal -->
                                        <div class="modal fade" id="editFruitModal${fruit.fruitId}" tabindex="-1" 
                                             aria-labelledby="editFruitModalLabel${fruit.fruitId}" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="editFruitModalLabel${fruit.fruitId}">Edit Fruit</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <form action="${pageContext.request.contextPath}/shop/edit-fruit" method="post">
                                                        <div class="modal-body">
                                                            <input type="hidden" name="fruitId" value="${fruit.fruitId}">
                                                            
                                                            <div class="mb-3">
                                                                <label for="fruitName${fruit.fruitId}" class="form-label">Fruit Name</label>
                                                                <input type="text" class="form-control" id="fruitName${fruit.fruitId}" 
                                                                       name="fruitName" value="${fruit.fruitName}" required>
                                                            </div>
                                                            
                                                            <div class="mb-3">
                                                                <label for="sourceCityId${fruit.fruitId}" class="form-label">Source City</label>
                                                                <select class="form-select" id="sourceCityId${fruit.fruitId}" 
                                                                        name="sourceCityId" required>
                                                                    <c:forEach var="city" items="${cities}">
                                                                        <option value="${city.cityId}" 
                                                                                <c:if test="${city.cityId == fruit.sourceCityId}">selected</c:if>>
                                                                            ${city.cityName}
                                                                        </option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                            <button type="submit" class="btn btn-primary">Save Changes</button>
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
                </div>
            </div>
        </div>
    </div>
</c:if>
