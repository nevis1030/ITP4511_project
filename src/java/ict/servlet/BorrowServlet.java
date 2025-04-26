/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.BorrowBean;
import ict.bean.FruitBean;
import ict.bean.ShopBean;
import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle borrowing data display and creation
 * 
 * @author local_user
 */
@WebServlet(name = "BorrowServlet", urlPatterns = {"/shop/borrow"})
public class BorrowServlet extends BaseServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves borrowing data and forwards to borrowing.jsp for display
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        
        // Get the user from session
        UserBean user = (UserBean) session.getAttribute("user");
        String shopId = user.getShopId();
        
        // Store shopId in session for easy access in JSP
        session.setAttribute("shopId", shopId);
        
        ProjectDB db = ProjectDB.getInstance();
        
        // Get all borrow records - ensure this is not null
        ArrayList<BorrowBean> borrows = db.listAllBorrow();
        System.out.println("Retrieved " + (borrows != null ? borrows.size() : "null") + " borrow records");
        
        // Get all fruits for reference (to display fruit names)
        ArrayList<FruitBean> fruits = db.listAllFruits();
        
        // Get only shops in the same city as the current shop
        ArrayList<ShopBean> shops = db.getShopsInSameCity(shopId);
        
        // Create a map to store shop names for display - include all shops for table display
        ArrayList<ShopBean> allShops = db.listAllShops();
        HashMap<String, String> shopNames = new HashMap<>();
        
        System.out.println("Total shops in the same city: " + (shops != null ? shops.size() : "null"));
        System.out.println("Total shops in database: " + (allShops != null ? allShops.size() : "null"));
        
        // Store all shop names for display in the table
        for (ShopBean s : allShops) {
            shopNames.put(s.getShopId(), s.getShopName());
        }
        
        // Debug shops in the same city
        for (ShopBean s : shops) {
            System.out.println("Shop in same city: " + s.getShopId() + " - " + s.getShopName() + " (City: " + s.getCityId() + ")");
        }
        
        System.out.println("Total shops for dropdown: " + shops.size());
        System.out.println("Total shop names for display: " + shopNames.size());
        
        // Set the data as request attributes
        request.setAttribute("borrows", borrows);
        request.setAttribute("fruits", fruits);
        request.setAttribute("shops", shops);
        request.setAttribute("shopNames", shopNames);
        
        // Forward to the dashboard with borrowing tab
        String redirectUrl = "/shop_dashboard.jsp?tab=borrowing";
        
        // If a subtab is specified in the URL, preserve it, otherwise default to borrow-request
        String subtab = request.getParameter("subtab");
        if (subtab != null && !subtab.isEmpty()) {
            redirectUrl += "&subtab=" + subtab;
        } else {
            redirectUrl += "&subtab=borrow-request";
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
        rd.forward(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * Creates a new borrow request based on form submission
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
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            // Get parameters for creating a new borrow request
            String fromShopId = request.getParameter("fromShopId");
            String toShopId = request.getParameter("toShopId");
            String fruitId = request.getParameter("fruitId");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Security check - ensure user is only creating requests from their own shop
            if (!userShopId.equals(fromShopId)) {
                // Redirect with error message if trying to create a request from another shop
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-request&error=Unauthorized: You can only create requests from your own shop");
                return;
            }
            
            try {
                // Create borrow request
                ProjectDB db = ProjectDB.getInstance();
                db.createBorrowRequest(fromShopId, toShopId, fruitId, quantity);
                
                // Redirect back to borrowing page with success message
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&success=Borrow request created successfully");
            } catch (Exception e) {
                // Handle errors
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-request&error=" + e.getMessage());
            }
        } else {
            // Unknown action
            response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-request&error=Invalid action");
        }
    }
}
