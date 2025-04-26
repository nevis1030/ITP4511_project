package ict.servlet;

import ict.bean.FruitBean;
import ict.db.ProjectDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling edit operations on fruits
 */
@WebServlet(name = "ShopFruitEditServlet", urlPatterns = {"/shop/edit-fruit"})
public class ShopFruitEditServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/shop/add-fruit");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        // Get form parameters
        String fruitId = request.getParameter("fruitId");
        String fruitName = request.getParameter("fruitName");
        String sourceCityId = request.getParameter("sourceCityId");
        
        if (fruitId == null || fruitId.isEmpty() || 
            fruitName == null || fruitName.isEmpty() || 
            sourceCityId == null || sourceCityId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + 
                    "/shop/add-fruit?error=Missing required fields");
            return;
        }
        
        try {
            // Update fruit
            ProjectDB db = ProjectDB.getInstance();
            FruitBean fruit = new FruitBean();
            fruit.setFruitId(fruitId);
            fruit.setFruitName(fruitName);
            fruit.setSourceCityId(sourceCityId);
            
            db.updateFruit(fruit);
            
            // Redirect to ShopAddFruitServlet with success message
            response.sendRedirect(request.getContextPath() + 
                    "/shop/add-fruit?success=Fruit updated successfully");
            
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + 
                    "/shop/add-fruit?error=" + e.getMessage());
        }
    }
}
