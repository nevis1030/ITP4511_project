package ict.servlet;

import ict.bean.ShopConsumptionBean;
import ict.bean.FruitBean;
import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author local_user
 */
@WebServlet(name="ShopConsumptionServlet", urlPatterns="/shop/consumption")
public class ShopConsumptionServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        
        // Get the user from session
        UserBean user = (UserBean) session.getAttribute("user");
        String shopId = user.getShopId();
        
        ProjectDB db = ProjectDB.getInstance();
        
        // Add sample consumption data for testing
        db.addSampleConsumptionData();
        
        // Get the region ID for the shop
        String regionId = db.getRegionIdForShop(shopId);
        
        // Get consumption data for the region
        ArrayList<ShopConsumptionBean> consumptions = db.listAllConsumption(regionId);
        
        // Get available options for edit modal
        ArrayList<FruitBean> availableFruits = db.getFruitsInRegion(regionId);
        ArrayList<String> availableRegions = db.getAllRegions();
        
        // Set the data as request attributes
        request.setAttribute("consumptions", consumptions);
        request.setAttribute("availableFruits", availableFruits);
        request.setAttribute("availableRegions", availableRegions);
        
        // Check if there's a tab parameter in the URL, preserve it when forwarding
        String tab = request.getParameter("tab");
        String redirectUrl = "/shop_dashboard.jsp";
        
        // If a tab is specified in the URL, preserve it, otherwise default to consumption tab
        if (tab != null && !tab.isEmpty()) {
            redirectUrl += "?tab=" + tab;
        } else {
            redirectUrl += "?tab=consumption";
        }
        
        // Forward to shop dashboard with the appropriate tab
        RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        String shopId = user.getShopId();
        
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            String consumptionId = request.getParameter("consumptionId");
            
            try {
                ProjectDB db = ProjectDB.getInstance();
                
                // Security check - get the consumption record and verify it's in the same region
                ShopConsumptionBean consumption = db.getConsumptionById(consumptionId);
                if (consumption == null) {
                    response.sendRedirect(request.getContextPath() + "/shop/consumption?error=Record not found");
                    return;
                }
                
                String regionId = db.getRegionIdForShop(shopId);
                if (!regionId.equals(consumption.getRegionId())) {
                    response.sendRedirect(request.getContextPath() + "/shop/consumption?error=Unauthorized access");
                    return;
                }
                
                db.deleteConsumption(consumptionId);
                
                // Redirect back to consumption page with success message
                response.sendRedirect(request.getContextPath() + "/shop/consumption?success=Record deleted successfully");
            } catch (Exception e) {
                // Handle errors
                response.sendRedirect(request.getContextPath() + "/shop/consumption?error=" + e.getMessage());
            }
        } else if ("edit".equals(action)) {
            try {
                String consumptionId = request.getParameter("consumptionId");
                String fruitId = request.getParameter("fruitId");
                String regionId = request.getParameter("regionId");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int season = Integer.parseInt(request.getParameter("season"));
                
                if (consumptionId == null || fruitId == null || regionId == null || 
                    request.getParameter("quantity") == null || request.getParameter("season") == null) {
                    throw new IllegalArgumentException("Missing required parameters");
                }
                
                // Security check - get the consumption record and verify it's in the same region
                ProjectDB db = ProjectDB.getInstance();
                ShopConsumptionBean consumption = db.getConsumptionById(consumptionId);
                if (consumption == null) {
                    response.sendRedirect(request.getContextPath() + "/shop/consumption?error=Record not found");
                    return;
                }
                
                String userRegionId = db.getRegionIdForShop(shopId);
                if (!userRegionId.equals(consumption.getRegionId())) {
                    response.sendRedirect(request.getContextPath() + "/shop/consumption?error=Unauthorized access");
                    return;
                }
                
                db.updateConsumption(consumptionId, fruitId, regionId, quantity, season);
                
                // Redirect back to consumption page with success message
                response.sendRedirect(request.getContextPath() + "/shop/consumption?success=Record updated successfully");
            } catch (NumberFormatException e) {
                // Invalid input format
                response.sendRedirect(request.getContextPath() + "/shop/consumption?error=Invalid input format");
            } catch (IllegalArgumentException e) {
                // Missing parameters
                response.sendRedirect(request.getContextPath() + "/shop/consumption?error=Missing required parameters");
            } catch (Exception e) {
                // Other errors
                response.sendRedirect(request.getContextPath() + "/shop/consumption?error=" + e.getMessage());
            }
        }
    }
}
