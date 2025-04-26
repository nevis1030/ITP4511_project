/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.ReservationBean;
import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle approving/denying reservation requests
 * 
 * @author local_user
 */
@WebServlet(name = "ReserveEditServlet", urlPatterns = {"/shop/reserve/edit"})
public class ReserveEditServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        String userShopId = user.getShopId();
        
        // Get form parameters
        String reservationId = request.getParameter("reservationId");
        String action = request.getParameter("action");
        
        if (reservationId == null || reservationId.isEmpty() || action == null || action.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&error=Invalid request parameters");
            return;
        }
        
        try {
            ProjectDB db = ProjectDB.getInstance();
            
            // Get the reservation to verify shop
            ReservationBean targetReservation = db.listAllReservation(reservationId);
            
            if (targetReservation == null) {
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&error=Reservation not found");
                return;
            }
            
            System.out.println("Found reservation: " + targetReservation);
            
            // Security check - ensure user is only managing reservations for their shop
            if (!userShopId.equals(targetReservation.getShopId())) {
                // Redirect with error message if trying to manage reservation for another shop
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&error=Unauthorized: You can only manage reservations for your shop");
                return;
            }
            
            // Process the action
            int status = 0;
            if ("approve".equals(action)) {
                status = 1; // Approved
                db.approveReservation(reservationId, status);
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&success=Reservation approved successfully");
            } else if ("deny".equals(action)) {
                status = 2; // Denied
                db.approveReservation(reservationId, status);
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&success=Reservation denied successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&error=Invalid action");
            }
        } catch (Exception e) {
            // Handle errors
            response.sendRedirect(request.getContextPath() + "/shop/reserve?subtab=record&error=" + e.getMessage());
        }
    }
}
