package ict.servlet;

import ict.db.ProjectDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for handling delete operations on fruits
 */
@WebServlet(name = "ShopFruitDeleteServlet", urlPatterns = {"/shop/delete-fruit"})
public class ShopFruitDeleteServlet extends BaseServlet {
    
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
        
        // Get fruit ID from form
        String fruitId = request.getParameter("fruitId");
        
        if (fruitId == null || fruitId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + 
                    "/shop/add-fruit?error=No fruit ID provided");
            return;
        }
        
        try {
            // Delete the fruit
            ProjectDB db = ProjectDB.getInstance();
            db.deleteFruit(fruitId);
            
            // Redirect to ShopAddFruitServlet with success message
            response.sendRedirect(request.getContextPath() + 
                    "/shop/add-fruit?success=Fruit deleted successfully");
            
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + 
                    "/shop/add-fruit?error=" + e.getMessage());
        }
    }
}
