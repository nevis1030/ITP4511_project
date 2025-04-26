
package ict.servlet;

import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "ShopStockDeleteServlet", urlPatterns = {"/shop/stock/delete"})
public class ShopStockDeleteServlet extends BaseServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        String userShopId = user.getShopId();
        
        // Get the stock ID to delete
        String stockId = request.getParameter("stockId");
        
        if (stockId == null || stockId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shop/stock?error=Invalid stock ID");
            return;
        }
        
        try {
            ProjectDB db = ProjectDB.getInstance();
            
            // Extract shop ID from stock ID to verify authorization
            // Stock ID format is shopId + fruitId, so we need to extract the shopId part
            // This is a simplistic approach assuming a specific format - might need adjustment
            String stockShopId = stockId.substring(0, stockId.length() - 4); // Assuming fruitId is 4 chars like "f001"
            
            // Security check - ensure user is only deleting their own shop's stock
            if (!userShopId.equals(stockShopId)) {
                // Redirect with error message if trying to delete another shop's stock
                response.sendRedirect(request.getContextPath() + "/shop/stock?error=Unauthorized delete attempt");
                return;
            }
            
            // Call the database method to delete the stock
            // Note: ProjectDB doesn't have a direct deleteStock method,
            // so we'll set the quantity to 0 using updateStock
            
            // Create a StockLevelBean with quantity of -999999 to effectively zero out the stock
            // This is a workaround since we don't have a direct delete method
            // The updateStock method adds the quantity value to the existing quantity
            
            db.deleteStock(stockId);
            
            // Redirect back to stock page with success message
            response.sendRedirect(request.getContextPath() + "/shop/stock?success=Stock item deleted successfully");
            
        } catch (Exception e) {
            // Handle errors
            response.sendRedirect(request.getContextPath() + "/shop/stock?error=" + e.getMessage());
        }
    }
}
