/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.StockLevelBean;
import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle editing stock quantity
 * 
 * @author local_user
 */
@WebServlet(name = "ShopStockEditServlet", urlPatterns = {"/shop/stock/edit"})
public class ShopStockEditServlet extends BaseServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     * Updates stock quantity based on form submission
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        String userShopId = user.getShopId();
        
        // Get form parameters
        String stockId = request.getParameter("stockId");
        String shopId = request.getParameter("shopId");
        String fruitId = request.getParameter("fruitId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        // Security check - ensure user is only modifying their own shop's stock
        if (!userShopId.equals(shopId)) {
            // Redirect with error message if trying to modify another shop's stock
            response.sendRedirect(request.getContextPath() + "/shop/stock?error=unauthorized");
            return;
        }
        
        try {
            // Create StockLevelBean object with the updated data
            StockLevelBean stock = new StockLevelBean();
            stock.setStockId(stockId);
            stock.setShopId(shopId);
            stock.setFruitId(fruitId);
            
            // Calculate the difference between new and old quantity
            // First get the current stock level
            ProjectDB db = ProjectDB.getInstance();
            StockLevelBean currentStock = db.getStockByShopAndFruit(shopId, fruitId);
            
            if (currentStock != null) {
                // Calculate the difference to add/subtract
                int quantityDifference = quantity - currentStock.getQuantity();
                stock.setQuantity(quantityDifference); // updateStock adds this to the existing quantity
                
                // Update the stock quantity
                db.updateStock(stock);
                
                // Redirect back to stock page with success message
                response.sendRedirect(request.getContextPath() + "/shop/stock?success=Stock quantity updated successfully");
            } else {
                // Stock not found - should not happen but handle it
                response.sendRedirect(request.getContextPath() + "/shop/stock?error=Stock not found");
            }
        } catch (NumberFormatException e) {
            // Invalid quantity format
            response.sendRedirect(request.getContextPath() + "/shop/stock?error=Invalid quantity format");
        } catch (Exception e) {
            // Other errors
            response.sendRedirect(request.getContextPath() + "/shop/stock?error=" + e.getMessage());
        }
    }
}
