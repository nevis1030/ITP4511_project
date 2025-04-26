/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.FruitBean;
import ict.bean.ReservationBean;
import ict.bean.UserBean;
import ict.bean.CityBean;
import ict.db.ProjectDB;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle reservation data display and creation
 * 
 * @author local_user
 */
@WebServlet(name = "ReserveServlet", urlPatterns = {"/shop/reserve"})
public class ReserveServlet extends BaseServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves reservation data and forwards to reserving.jsp for display
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
        
        // Get reservations for the current shop
        ArrayList<ReservationBean> reservations = db.getReservationByShop(shopId);
        System.out.println("Retrieved " + (reservations != null ? reservations.size() : "null") + " reservation records for shop: " + shopId);
        
        // Get all fruits for reference (to display fruit names)
        ArrayList<FruitBean> fruits = db.listAllFruits();
        
        // Get all cities for reference (to display source city names)
        ArrayList<CityBean> cities = db.listAllCities();
        
        // Set the data as request attributes
        request.setAttribute("reservations", reservations);
        request.setAttribute("fruits", fruits);
        request.setAttribute("cities", cities);
        
        // Forward to the dashboard with reserving tab
        String redirectUrl = "/shop_dashboard.jsp?tab=reserving";
        
        // If a subtab is specified in the URL, preserve it, otherwise default to request
        String subtab = request.getParameter("subtab");
        if (subtab != null && !subtab.isEmpty()) {
            redirectUrl += "&subtab=" + subtab;
        } else {
            redirectUrl += "&subtab=request";
        }
        
        RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
        rd.forward(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     * Creates a new reservation request based on form submission
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
            // Get parameters for creating a new reservation
            String shopId = request.getParameter("shopId");
            String fruitId = request.getParameter("fruitId");
            String orderDateStr = request.getParameter("orderDate");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Security check - ensure user is only creating reservations for their own shop
            if (!userShopId.equals(shopId)) {
                // Redirect with error message if trying to create a reservation for another shop
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=request&error=Unauthorized: You can only create reservations for your own shop");
                return;
            }
            
            try {
                // Parse the user-selected order date
                Date orderDate = Date.valueOf(orderDateStr);
                
                // Validate date is within allowed range (tomorrow to 14 days from now)
                LocalDate today = LocalDate.now();
                LocalDate tomorrow = today.plusDays(1);
                LocalDate maxDate = today.plusDays(14);
                LocalDate selectedDate = orderDate.toLocalDate();
                
                if (selectedDate.isBefore(tomorrow)) {
                    response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=request&error=Order date must be at least tomorrow");
                    return;
                }
                
                if (selectedDate.isAfter(maxDate)) {
                    response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=request&error=Order date cannot be more than 14 days in the future");
                    return;
                }
                
                // Create reservation request with user-selected date
                ProjectDB db = ProjectDB.getInstance();
                db.createReservation(fruitId, shopId, quantity, orderDate);
                
                // Redirect back to reserving page with success message
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&success=Reservation created successfully");
            } catch (Exception e) {
                // Handle errors
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=request&error=" + e.getMessage());
            }
        } else {
            // Unknown action
            response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=request&error=Invalid action");
        }
    }
}
